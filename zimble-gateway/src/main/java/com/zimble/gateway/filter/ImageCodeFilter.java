package com.zimble.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zimble.gateway.common.core.constant.Constants;
import com.zimble.gateway.common.core.domain.R;
import com.zimble.gateway.common.core.exception.ValidateCodeException;
import com.zimble.gateway.common.core.utils.StringUtils;
import com.zimble.gateway.common.redis.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 验证码处理
 * 
 * @author zimble
 */
@SuppressWarnings("rawtypes")
@Component
public class ImageCodeFilter extends AbstractGatewayFilterFactory
{
    private static final Logger log = LoggerFactory.getLogger(ImageCodeFilter.class);
    
    private final static String AUTH_URL = "/oauth/token";

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private RedisService redisService;

    @Override
    public GatewayFilter apply(Object config)
    {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            URI uri = request.getURI();
            // 不是登录请求，直接向下执行
            if (!StringUtils.containsIgnoreCase(uri.getPath(), AUTH_URL))
            {
                return chain.filter(exchange);
            }
            try
            {
                String code = request.getQueryParams().getFirst("code");
                String uuid = request.getQueryParams().getFirst("uuid");
                checkCode(code, uuid);
            }
            catch (Exception e)
            {
                ServerHttpResponse response = exchange.getResponse();
                try
                {
                    return exchange.getResponse().writeWith(Mono.just(response.bufferFactory()
                            .wrap(objectMapper.writeValueAsBytes(
                                R.failed(e.getMessage())))));
                }
                catch (JsonProcessingException e1)
                {
                    log.error("对象输出异常", e1);
                }
            }
            return chain.filter(exchange);
        };
    }

    /**
     * 校验验证码
     */
    private void checkCode(String code, String uuid) throws ValidateCodeException
    {
        if (StringUtils.isEmpty(code))
        {
            throw new ValidateCodeException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid))
        {
            throw new ValidateCodeException("验证码已失效");
        }
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);
        
        if (!code.equalsIgnoreCase(captcha))
        {
            throw new ValidateCodeException("验证码错误");
        }
    }
}
