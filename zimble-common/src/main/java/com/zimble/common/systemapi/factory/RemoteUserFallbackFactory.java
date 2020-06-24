package com.zimble.common.systemapi.factory;

import com.zimble.common.core.domain.R;
import com.zimble.common.systemapi.model.UserInfo;
import com.zimble.common.systemapi.service.RemoteUserService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable)
    {
        log.error("feign 查询用户信息失败:{}", throwable.getMessage());
        return new RemoteUserService()
        {
            @Override
            public R<UserInfo> info(String username, String from)
            {
                return null;
            }
        };
    }
}
