package com.pallas.server.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/8/13 13:36
 * @desc:
 */
@RestController
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.pallas")
public class GatewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }
}
