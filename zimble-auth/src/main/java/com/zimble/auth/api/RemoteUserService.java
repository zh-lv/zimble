package com.zimble.auth.api;

import com.zimble.auth.api.factory.RemoteUserFallbackFactory;
import com.zimble.auth.api.model.UserInfo;
import com.zimble.auth.common.core.constant.SecurityConstants;
import com.zimble.auth.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "zimble-system", fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{
    @RequestMapping(value = "/user/info/{username}", method = RequestMethod.GET)
    public R<UserInfo> info(@PathVariable("username") String username,
                            @RequestHeader(SecurityConstants.FROM) String from);
}
