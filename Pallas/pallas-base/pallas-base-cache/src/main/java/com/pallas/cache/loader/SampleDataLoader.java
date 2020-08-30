package com.pallas.cache.loader;

import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/8/30 15:14
 * @desc:
 */
@Component
public class SampleDataLoader extends AbstractDataloader<Sample> {

  private static final String key = "sample";

  @Override
  public boolean ifExist() {
    return this.getOperations().hasKey(key).block();
  }

  @Override
  public void clear() {
    long value = this.getOperations().delete(key).block();
    System.out.println(value);
  }

  @Override
  public Sample loadData() {
    return new Sample(1L, "sdfsdfsd");
  }

  @Override
  public void cacheData(Sample data) {
    this.getOperations().opsForValue().set(key, data).subscribe();
  }

  @Override
  public Sample getCache() {
    Sample sample = this.getOperations().opsForValue().get(key).block();
    return sample;
  }
}
