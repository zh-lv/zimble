package com.zimble.common.security.annotation;

import com.zimble.common.security.component.ZimbleResourceServerAutoConfiguration;
import com.zimble.common.security.component.ZimbleSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import java.lang.annotation.*;

/**
 * 自定义资源服务注解
 * 
 * @author zimble
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ZimbleResourceServerAutoConfiguration.class, ZimbleSecurityBeanDefinitionRegistrar.class })
public @interface EnableZimbleResourceServer
{

}
