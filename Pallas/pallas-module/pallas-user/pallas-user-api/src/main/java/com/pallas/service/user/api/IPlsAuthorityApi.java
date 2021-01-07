package com.pallas.service.user.api;

import java.util.Map;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/12/17 15:59
 * @desc: 权限接口
 */
public interface IPlsAuthorityApi {

    /**
     * 获取资源权限
     *
     * @param resourceType
     * @param resource
     * @return
     */
    Integer getAuthority(String resourceType, Long resource);

    /**
     * 获取资源权限
     *
     * @param resources
     * @return
     */
    Map<Long, Integer> getAuthorityMap(Set<Long> resources);

}
