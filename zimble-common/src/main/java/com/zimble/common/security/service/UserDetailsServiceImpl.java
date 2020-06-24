package com.zimble.common.security.service;

import com.zimble.common.core.constant.SecurityConstants;
import com.zimble.common.core.domain.R;
import com.zimble.common.core.enums.UserStatus;
import com.zimble.common.core.exception.BaseException;
import com.zimble.common.core.utils.StringUtils;
import com.zimble.common.security.domain.LoginUser;
import com.zimble.common.systemapi.domain.dto.SysUserDTO;
import com.zimble.common.systemapi.model.UserInfo;
import com.zimble.common.systemapi.service.RemoteUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户详细信息
 *
 * @author zimble
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private RemoteUserService remoteUserService;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        R<UserInfo> userResult = remoteUserService.info(username, SecurityConstants.FROM_IN);
        checkUser(userResult, username);
        return getUserDetails(userResult);
    }

    public void checkUser(R<UserInfo> userResult, String username)
    {
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData()))
        {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("登录用户：" + username + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(userResult.getData().getSysUser().getDelFlag()))
        {
            log.info("登录用户：{} 已被删除.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(userResult.getData().getSysUser().getStatus()))
        {
            log.info("登录用户：{} 已被停用.", username);
            throw new BaseException("对不起，您的账号：" + username + " 已停用");
        }
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return
     */
    private UserDetails getUserDetails(R<UserInfo> result)
    {
        UserInfo info = result.getData();
        Set<String> dbAuthsSet = new HashSet<String>();
        if (StringUtils.isNotEmpty(info.getRoles()))
        {
            // 获取角色
            Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
            // 获取资源
            dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));
        }

        Collection<? extends GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        SysUserDTO user = info.getSysUser();

        // 构造security用户
        return new LoginUser(user.getUserId(), user.getDeptId(), user.getUserName(), user.getPassword(), true, true,
                true, true, authorities);
    }
}
