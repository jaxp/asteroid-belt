package com.pallas.server.cloud.file;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: jax
 * @time: 2020/11/26 16:06
 * @desc:
 */
@RefreshScope
@EnableFeignClients("com.pallas")
@SpringBootApplication(scanBasePackages = "com.pallas")
@MapperScan("com.pallas.service")
public class FileApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
