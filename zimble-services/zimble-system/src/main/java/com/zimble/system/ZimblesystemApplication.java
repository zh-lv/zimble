package com.zimble.system;

import com.zimble.common.security.annotation.EnableZimbleFeignClients;
import com.zimble.common.security.annotation.EnableZimbleResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableZimbleResourceServer
@SpringCloudApplication
@EnableZimbleFeignClients
@MapperScan("com.zimble.*.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class ZimblesystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZimblesystemApplication.class, args);
        System.out.println("--------------   核心基础服务启动成功!   --------------");
    }

}
