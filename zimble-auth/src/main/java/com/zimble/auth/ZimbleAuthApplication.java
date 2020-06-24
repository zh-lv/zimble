package com.zimble.auth;

import com.zimble.auth.security.annotation.EnableZimbleFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableZimbleFeignClients
public class ZimbleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZimbleAuthApplication.class, args);
        System.out.println("--------------   安全认证服务启动成功!   --------------");

    }

}
