package com.pallas.server.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: jax
 * @time: 2020/12/11 13:16
 * @desc:
 */
@RefreshScope
@EnableFeignClients("com.pallas")
@SpringBootApplication(scanBasePackages = "com.pallas")
@MapperScan("com.pallas.service")
public class DashboardApplication {
    public static void main(String[] args) {
        SpringApplication.run(DashboardApplication.class, args);
    }
}
