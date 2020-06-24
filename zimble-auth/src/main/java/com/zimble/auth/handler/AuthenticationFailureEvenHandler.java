package com.zimble.auth.handler;

import com.zimble.auth.common.core.utils.ServletUtils;
import com.zimble.auth.security.handler.AbstractAuthenticationFailureEvenHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证失败处理
 * 
 * @author zimble
 */
@Component
public class AuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler
{
    private final Logger logger = LoggerFactory.getLogger(AuthenticationSuccessEventHandler.class);

    @Override
    public void handle(AuthenticationException authenticationException, Authentication authentication)
    {
        HttpServletRequest request = ServletUtils.getRequest();

        String url = request.getRequestURI();

        String username = (String) authentication.getPrincipal();

        logger.info("用户：{} 授权失败，url：{}", username, url);
    }
}
