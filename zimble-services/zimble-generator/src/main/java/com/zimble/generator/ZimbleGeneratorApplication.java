package com.zimble.generator;

import com.zimble.common.security.annotation.EnableZimbleFeignClients;
import com.zimble.common.security.annotation.EnableZimbleResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableZimbleResourceServer
@SpringCloudApplication
@EnableZimbleFeignClients
@MapperScan("com.zimble.*.mapper")
public class ZimbleGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZimbleGeneratorApplication.class, args);
        System.out.println("--------------   代码生成服务模块启动成功!   --------------");

    }

}
