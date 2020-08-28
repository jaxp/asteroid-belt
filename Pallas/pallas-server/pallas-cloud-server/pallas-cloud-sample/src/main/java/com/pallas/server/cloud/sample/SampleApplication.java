package com.pallas.server.cloud.sample;

import com.pallas.server.cloud.sample.clients.PlsUserClient;
import com.pallas.service.user.dto.PlsUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author: jax
 * @time: 2020/8/11 19:58
 * @desc:
 */
@RestController
@RefreshScope
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.pallas")
public class SampleApplication {
  @Autowired
  private PlsUserClient userClient;

  @Value("${aaaaa}")
  private String aaaaa;

  @RequestMapping("/")
  public PlsUserDTO home() {
    return userClient.getUser("user");
  }

  public static void main(String[] args) {
    SpringApplication.run(SampleApplication.class, args);
  }

}
