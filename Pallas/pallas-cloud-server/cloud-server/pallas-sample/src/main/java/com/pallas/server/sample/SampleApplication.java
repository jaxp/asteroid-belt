package com.pallas.server.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jax
 * @time: 2020/8/11 19:58
 * @desc:
 */
@SpringBootApplication
@RestController
@RefreshScope
public class SampleApplication {

  @Value("${aaaaa}")
  private String aaaaa;

  @RequestMapping("/")
  public String home() {
    return aaaaa;
  }

  public static void main(String[] args) {
    SpringApplication.run(SampleApplication.class, args);
  }

}
