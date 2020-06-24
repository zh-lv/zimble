package com.zimble.job;

import com.zimble.common.security.annotation.EnableZimbleFeignClients;
import com.zimble.common.security.annotation.EnableZimbleResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableZimbleResourceServer
@SpringCloudApplication
@EnableZimbleFeignClients
@MapperScan("com.zimble.*.mapper")
public class ZimbleJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZimbleJobApplication.class, args);
        System.out.println("--------------   定时任务模块启动成功!   --------------");
    }

}
