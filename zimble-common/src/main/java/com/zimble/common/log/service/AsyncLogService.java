package com.zimble.common.log.service;

import com.zimble.common.core.constant.SecurityConstants;
import com.zimble.common.systemapi.domain.dto.SysOperLogDTO;
import com.zimble.common.systemapi.service.RemoteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 * 
 * @author zimble
 */
@Service
public class AsyncLogService
{
    @Autowired
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLogDTO sysOperLog)
    {
        remoteLogService.saveLog(sysOperLog, SecurityConstants.FROM_IN);
    }
}
