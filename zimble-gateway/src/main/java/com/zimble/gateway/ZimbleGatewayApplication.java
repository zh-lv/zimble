package com.zimble.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ZimbleGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZimbleGatewayApplication.class, args);
        System.out.println("--------------   路由服务启动成功!   --------------");

    }

}
