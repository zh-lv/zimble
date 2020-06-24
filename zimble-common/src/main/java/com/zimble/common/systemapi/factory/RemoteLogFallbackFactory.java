package com.zimble.common.systemapi.factory;

import com.zimble.common.core.domain.R;
import com.zimble.common.systemapi.domain.dto.SysOperLogDTO;
import com.zimble.common.systemapi.service.RemoteLogService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志服务降级处理
 * 
 * @author zimble
 */
@Component
public class RemoteLogFallbackFactory implements FallbackFactory<RemoteLogService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteLogFallbackFactory.class);

    @Override
    public RemoteLogService create(Throwable throwable)
    {
        log.error("日志服务调用失败:{}", throwable.getMessage());
        return new RemoteLogService()
        {
            @Override
            public R<Boolean> saveLog(SysOperLogDTO sysOperLog, String from)
            {
                return null;
            }
        };
    }
}
