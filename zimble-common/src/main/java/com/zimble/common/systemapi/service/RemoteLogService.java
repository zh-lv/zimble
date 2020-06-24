package com.zimble.common.systemapi.service;

import com.zimble.common.core.constant.SecurityConstants;
import com.zimble.common.core.constant.ServiceNameConstants;
import com.zimble.common.core.domain.R;
import com.zimble.common.systemapi.domain.dto.SysOperLogDTO;
import com.zimble.common.systemapi.factory.RemoteLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 日志服务
 * 
 * @author zimble
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService
{
    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @param from 内部调用标志
     * @return 结果
     */
    @PostMapping("/operlog")
    R<Boolean> saveLog(@RequestBody SysOperLogDTO sysOperLog, @RequestHeader(SecurityConstants.FROM) String from);
}
