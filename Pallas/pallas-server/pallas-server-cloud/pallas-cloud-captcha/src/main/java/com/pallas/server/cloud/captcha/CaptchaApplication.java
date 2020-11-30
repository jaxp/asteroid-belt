package com.pallas.server.cloud.captcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: jax
 * @time: 2020/11/26 16:01
 * @desc:
 */
@RefreshScope
@EnableFeignClients("com.pallas")
@SpringBootApplication(scanBasePackages = "com.pallas")
public class CaptchaApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaptchaApplication.class, args);
    }
}
