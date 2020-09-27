package com.pallas.cache.cacher;

import com.pallas.cache.manager.CacheManager;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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
    private RedisTemplate<String, T> template;

    @Override
    public T getData() {
        if (this.ifExist()) {
          return this.getCache();
        }
        T original = this.loadData();
        cacheData(original);
        return original;
    }

    protected <T> RedisTemplate<String, T> buildTemplate(Class<T> clazz) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getCacheManager().getFactory());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(clazz));
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
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
        this.setTemplate(this.buildTemplate(this.getSuperclassTypeParameter()));
        cacheManager.register(this);
    }

    public Boolean expire(Duration duration) {
        return this.getTemplate().expire(getKey(), duration);
    }

}
