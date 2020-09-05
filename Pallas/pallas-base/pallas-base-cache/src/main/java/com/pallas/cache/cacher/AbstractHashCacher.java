package com.pallas.cache.cacher;

import org.springframework.data.redis.core.ReactiveHashOperations;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/8/31 9:37
 * @desc:
 */
public abstract class AbstractHashCacher<T> extends AbstractDataloader<Map<String, T>> {

  public ReactiveHashOperations<String, String, T> ops() {
    return this.getOperations().opsForHash();
  }

  @Override
  public boolean ifExist() {
    return this.getOperations().hasKey(this.getKey()).block();
  }

  @Override
  public void clear() {
    this.getOperations().delete(this.getKey()).subscribe();
  }

  @Override
  public void cacheData(Map<String, T> data) {
    this.ops().putAll(getKey(), data).subscribe();
  }

  @Override
  public Map<String, T> getCache() {
    Map<String, T> result = new HashMap<>();
    this.ops()
        .entries(getKey())
        .subscribe(e -> result.put(e.getKey(), e.getValue()));
    return result;
  }

  public T loadData(String key) {
    return null;
  }

  public T getData(String key) {
    if (this.ifExist(key)) {
      return this.getCache(key);
    }
    T data = this.loadData(key);
    cacheData(key, data);
    return data;
  }

  public boolean ifExist(String key) {
    return this.ops().hasKey(getKey(), key).block();
  }

  public void cacheData(String key, T data) {
    this.ops().put(getKey(), key, data).subscribe();
  }

  public T getCache(String key) {
    return this.ops().get(getKey(), key).block();
  }

}
