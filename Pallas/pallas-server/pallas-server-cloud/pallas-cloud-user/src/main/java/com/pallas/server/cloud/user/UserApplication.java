package com.pallas.server.cloud.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: jax
 * @time: 2020/8/13 14:29
 * @desc:
 */
@EnableFeignClients("com.pallas")
@SpringBootApplication(scanBasePackages = "com.pallas")
@MapperScan("com.pallas.service")
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
