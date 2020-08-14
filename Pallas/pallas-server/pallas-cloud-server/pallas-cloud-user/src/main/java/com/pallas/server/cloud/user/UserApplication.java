package com.pallas.server.cloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: jax
 * @time: 2020/8/13 14:29
 * @desc:
 */
@SpringBootApplication(scanBasePackages = "com.pallas")
@EnableFeignClients
public class UserApplication {
  public static void main(String[] args) {
    SpringApplication.run(UserApplication.class, args);
  }
}
