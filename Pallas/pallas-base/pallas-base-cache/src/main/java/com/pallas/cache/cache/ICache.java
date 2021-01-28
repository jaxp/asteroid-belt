package com.pallas.cache.cache;

/**
 * @author: jax
 * @time: 2020/8/30 14:31
 * @desc:
 */
public interface ICache<T> {

    /**
     * 缓存key
     *
     * @return
     */
    String getKey();

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
    Boolean ifExist();

    /**
     * 清空缓存
     * @return
     */
    Boolean clear();

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
