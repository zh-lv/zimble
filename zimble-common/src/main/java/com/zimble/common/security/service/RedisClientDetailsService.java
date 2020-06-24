package com.zimble.common.security.service;

import com.zimble.common.core.constant.CacheConstants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * 重写原生方法支持redis缓存
 *
 * @author zimble
 */
public class RedisClientDetailsService extends JdbcClientDetailsService
{
    public RedisClientDetailsService(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    @Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId)
    {
        return super.loadClientByClientId(clientId);
    }
}
