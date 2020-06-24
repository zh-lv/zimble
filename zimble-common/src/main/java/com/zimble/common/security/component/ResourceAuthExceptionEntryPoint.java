package com.zimble.common.security.component;

import com.alibaba.fastjson.JSON;
import com.zimble.common.core.constant.HttpStatus;
import com.zimble.common.core.domain.R;
import com.zimble.common.core.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败的异常
 * 
 * @author zimble
 */
@Component
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint
{
    private final Logger logger = LoggerFactory.getLogger(ResourceAuthExceptionEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException
    {
        logger.info("令牌不合法，禁止访问 {}", request.getRequestURI());

        String msg = authException.getMessage();
        ServletUtils.renderString(response, JSON.toJSONString(R.failed(HttpStatus.UNAUTHORIZED, msg)));
    }
}
