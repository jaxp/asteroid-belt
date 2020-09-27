package com.pallas.server.cloud.sample.conf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author: jax
 * @time: 2020/8/11 19:58
 * @desc:
 */
@SpringBootApplication
@EnableConfigServer
public class ConfApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfApplication.class, args);
    }

}
