package com.zimble.auth.config;

import com.zimble.auth.common.core.constant.CacheConstants;
import com.zimble.auth.common.core.constant.SecurityConstants;
import com.zimble.auth.security.component.ZimbleWebResponseExceptionTranslator;
import com.zimble.auth.security.domain.LoginUser;
import com.zimble.auth.security.service.RedisClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2 认证服务配置
 * 
 * @author zimble
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 定义授权和令牌端点以及令牌服务
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
    {
        endpoints
                // 请求方式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 指定token存储位置
                .tokenStore(tokenStore())
                // 自定义生成令牌
                .tokenEnhancer(tokenEnhancer())
                // 用户账号密码认证
                .userDetailsService(userDetailsService)
                // 指定认证管理器
                .authenticationManager(authenticationManager)
                // 是否重复使用 refresh_token
                .reuseRefreshTokens(false)
                // 自定义异常处理
                .exceptionTranslator(new ZimbleWebResponseExceptionTranslator());
    }

    /**
     * 配置令牌端点(Token Endpoint)的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer)
    {
        oauthServer.allowFormAuthenticationForClients().checkTokenAccess("permitAll()");
    }

    /**
     * 声明 ClientDetails实现
     */
    public RedisClientDetailsService clientDetailsService()
    {
        RedisClientDetailsService clientDetailsService = new RedisClientDetailsService(dataSource);
        clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
        clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        return clientDetailsService;
    }

    /**
     * 配置客户端详情
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 基于 Redis 实现，令牌保存到缓存
     */
    @Bean
    public TokenStore tokenStore()
    {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(CacheConstants.OAUTH_ACCESS);
        return tokenStore;
    }

    /**
     * 自定义生成令牌
     */
    @Bean
    public TokenEnhancer tokenEnhancer()
    {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(4);
            LoginUser user = (LoginUser) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(SecurityConstants.DETAILS_LICENSE, SecurityConstants.LICENSE);
            additionalInfo.put(SecurityConstants.DETAILS_USER_ID, user.getUserId());
            additionalInfo.put(SecurityConstants.DETAILS_USERNAME, user.getUsername());
            additionalInfo.put(SecurityConstants.DETAILS_DEPT_ID, user.getDeptId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
