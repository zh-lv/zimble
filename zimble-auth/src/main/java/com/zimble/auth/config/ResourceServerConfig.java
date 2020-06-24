package com.zimble.auth.config;

import com.zimble.auth.security.component.ResourceAuthExceptionEntryPoint;
import com.zimble.auth.security.handler.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter
{
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private ResourceAuthExceptionEntryPoint exceptionEntryPoint;

    @Override
    public void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .httpBasic();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
    {
        resources.authenticationEntryPoint(exceptionEntryPoint).accessDeniedHandler(accessDeniedHandler);
    }
}
