package com.pallas.server.cloud.gateway.filters;

import com.pallas.server.cloud.gateway.clients.PlsUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: jax
 * @time: 2020/9/4 16:34
 * @desc: 过滤权限
 */
@Component
public class AuthFilter implements GlobalFilter, Ordered {

  @Autowired
  private PlsUserClient plsUserClient;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    long a = plsUserClient.getUser("123");
    exchange.getRequest().mutate().header("123", "123").build();
    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return -1;
  }
}
