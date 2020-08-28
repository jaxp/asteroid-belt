package com.pallas.cache.loader;

import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

/**
 * @author: jax
 * @time: 2020/8/27 10:24
 * @desc:
 */
@Component
public class CacheLoader {

  private final ReactiveRedisConnectionFactory factory;
  private ReactiveRedisOperations<String, String> redisOperations;

  public CacheLoader(ReactiveRedisConnectionFactory factory) {
    this.factory = factory;
    this.redisOperations = new ReactiveStringRedisTemplate(factory);
  }

  @PostConstruct
  public void loadData() {
    factory.getReactiveConnection().serverCommands().flushAll().thenMany(
        Flux.just("r", "e", "d", "i", "s")
            .flatMap(str -> redisOperations.opsForValue().set(str, str)))
        .thenMany(redisOperations.keys("*")
            .flatMap(redisOperations.opsForValue()::get))
        .subscribe(System.out::println);
  }

}
