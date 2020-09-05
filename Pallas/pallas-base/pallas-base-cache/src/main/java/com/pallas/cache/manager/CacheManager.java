package com.pallas.cache.manager;

import com.pallas.cache.cacher.ICacher;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/8/27 10:24
 * @desc:
 */
@Component
public class CacheManager {

  private final ReactiveRedisConnectionFactory factory;
  private ReactiveRedisOperations<String, String> redisOperations;

  public CacheManager(ReactiveRedisConnectionFactory factory) {
    this.factory = factory;
    this.redisOperations = new ReactiveStringRedisTemplate(factory);
  }

  public void register(ICacher dataLoader) {
    if (dataLoader.clearOnInit() && dataLoader.ifExist()) {
      dataLoader.clear();
    }
  }

  public ReactiveRedisConnectionFactory getFactory() {
    return this.factory;
  }
}
