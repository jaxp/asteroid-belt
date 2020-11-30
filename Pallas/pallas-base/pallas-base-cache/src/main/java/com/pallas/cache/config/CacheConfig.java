package com.pallas.cache.config;

import com.pallas.cache.manager.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: jax
 * @time: 2020/11/28 14:48
 * @desc:
 */
@Configuration
public class CacheConfig {

    @Autowired
    private CacheManager cacheManager;

    @Bean("stringTemplate")
    public RedisTemplate<String, String> template() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(cacheManager.getFactory());
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
