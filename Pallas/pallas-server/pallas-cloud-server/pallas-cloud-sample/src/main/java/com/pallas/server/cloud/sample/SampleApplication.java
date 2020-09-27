package com.pallas.server.cloud.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: jax
 * @time: 2020/8/11 19:58
 * @desc:
 */
@RestController
@RefreshScope
@EnableFeignClients("com.pallas")
@SpringBootApplication(scanBasePackages = "com.pallas")
public class SampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }

}
