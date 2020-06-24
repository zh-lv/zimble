package com.zimble.common.systemapi.service;

import com.zimble.common.core.constant.SecurityConstants;
import com.zimble.common.core.domain.R;
import com.zimble.common.systemapi.factory.RemoteUserFallbackFactory;
import com.zimble.common.systemapi.model.UserInfo;
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
