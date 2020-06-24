package com.zimble.auth.handler;

import com.zimble.auth.common.core.utils.ServletUtils;
import com.zimble.auth.security.domain.LoginUser;
import com.zimble.auth.security.handler.AbstractAuthenticationSuccessEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证成功处理
 * 
 * @author zimble
 */
@Component
public class AuthenticationSuccessEventHandler extends AbstractAuthenticationSuccessEventHandler
{
    private final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessEventHandler.class);

    @Override
    public void handle(Authentication authentication)
    {
        HttpServletRequest request = ServletUtils.getRequest();

        String url = request.getRequestURI();

        if (authentication.getPrincipal() instanceof LoginUser)
        {
            LoginUser user = (LoginUser) authentication.getPrincipal();

            String username = user.getUsername();

            logger.info("用户：{} 授权成功，url：{}", username, url);
        }
    }
}
