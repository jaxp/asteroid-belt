package com.pallas.cache.manager;

import com.pallas.cache.cacher.ICacher;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/8/27 10:24
 * @desc:
 */
@Component
public class CacheManager {

    private final RedisConnectionFactory factory;

    public CacheManager(RedisConnectionFactory factory) {
        this.factory = factory;
    }

    public void register(ICacher dataLoader) {
        if (dataLoader.clearOnInit()) {
            if (dataLoader.ifExist()) {
                dataLoader.clear();
            }
        }
    }

    public RedisConnectionFactory getFactory() {
        return this.factory;
    }
}
