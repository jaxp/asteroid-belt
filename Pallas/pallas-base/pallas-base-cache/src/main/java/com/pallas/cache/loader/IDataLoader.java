package com.pallas.cache.loader;

/**
 * @author: jax
 * @time: 2020/8/30 14:31
 * @desc:
 */
public interface IDataLoader<T> {

  /**
   * 启动时清空
   *
   * @return
   */
  default boolean clearOnInit() {
    return false;
  }

  /**
   * 缓存是否已存在
   *
   * @return
   */
  boolean ifExist();

  /**
   * 清空缓存
   */
  void clear();

  /**
   * 加载数据
   *
   * @return
   */
  T loadData();

  /**
   * 缓存数据
   */
  void cacheData(T data);

  /**
   * 获取缓存
   *
   * @return
   */
  T getCache();

  /**
   * 获取数据
   *
   * @return
   */
  T getData();
}
