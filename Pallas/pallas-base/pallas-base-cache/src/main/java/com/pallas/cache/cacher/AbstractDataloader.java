package com.pallas.cache.cacher;

import com.pallas.cache.manager.CacheManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;

/**
 * @author: jax
 * @time: 2020/8/30 13:28
 * @desc:
 */
@Data
public abstract class AbstractDataloader<T> implements ICacher<T> {

  @Autowired
  private CacheManager cacheManager;
  private ReactiveRedisOperations<String, T> operations;

  @Override
  public T getData() {
    if (this.ifExist()) {
      return this.getCache();
    }
    T original = this.loadData();
    cacheData(original);
    return original;
  }

  protected <T> ReactiveRedisOperations<String, T> buildOperations(Class<T> clazz) {
    Jackson2JsonRedisSerializer<T> serializer = new Jackson2JsonRedisSerializer<>(clazz);

    RedisSerializationContext.RedisSerializationContextBuilder<String, T> builder =
        RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

    RedisSerializationContext<String, T> context = builder.value(serializer).build();

    return new ReactiveRedisTemplate<>(getCacheManager().getFactory(), context);
  }

  public Class getSuperclassTypeParameter() {
    Type superclass = getClass().getGenericSuperclass();
    if (superclass instanceof ParameterizedType) {
      ParameterizedType parameterized = (ParameterizedType) superclass;
      Class<T> entityClass = (Class<T>) parameterized.getActualTypeArguments()[0];
      return entityClass;
    }
    return String.class;
  }

  @PostConstruct
  public void after() {
    this.setOperations(this.buildOperations(this.getSuperclassTypeParameter()));
    cacheManager.register(this);
  }

  public void expire(Duration duration) {
    this.getOperations().expire(getKey(), duration).subscribe();
  }

}
