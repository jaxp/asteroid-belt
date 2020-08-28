//package com.pallas.server.cloud.gateway.filters;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
///**
// * @author: jax
// * @time: 2020/8/24 19:02
// * @desc:
// */
//@Slf4j
//@Component
//public class GatewayConfig {
//  @Bean
//  @Order(-1)
//  public GlobalFilter a() {
//    return (exchange, chain) -> {
//      log.info("first pre filter");
//      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//        log.info("third post filter");
//      }));
//    };
//  }
//
//  @Bean
//  @Order(0)
//  public GlobalFilter b() {
//    return (exchange, chain) -> {
//      log.info("second pre filter");
//      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//        log.info("second post filter");
//      }));
//    };
//  }
//
//  @Bean
//  @Order(1)
//  public GlobalFilter c() {
//    return (exchange, chain) -> {
//      log.info("third pre filter");
//      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//        log.info("first post filter");
//      }));
//    };
//  }
//}
