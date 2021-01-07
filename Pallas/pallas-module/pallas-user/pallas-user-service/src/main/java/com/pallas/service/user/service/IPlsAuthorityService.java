package com.pallas.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.user.api.IPlsAuthorityApi;
import com.pallas.service.user.bean.PlsAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/8/26 11:21
 * @desc:
 */
public interface IPlsAuthorityService extends IService<PlsAuthority>, IPlsAuthorityApi {

    /**
     * 获取权限
     *
     * @param organization
     * @return
     */
    List<PlsAuthority> getAllAuthorities(long organization);

    /**
     * 获取权限
     *
     * @param organization
     * @return
     */
    Map<String, List<PlsAuthority>> getAuthorityMap(long organization);

    /**
     * 获取组织权限
     *
     * @param organization
     * @param resourceTYpe
     * @return
     */
    List<PlsAuthority> getAuthorities(Long organization, String resourceTYpe);

    /**
     * 获取权限
     *
     * @param organizations
     * @return
     */
    List<PlsAuthority> getAllAuthorities(Collection<Long> organizations);

    /**
     * 获取权限
     *
     * @param organizations
     * @return
     */
    Map<String, List<PlsAuthority>> getAuthorityMap(Collection<Long> organizations);

    /**
     * 获取组织权限
     *
     * @param organizations
     * @param resourceTYpe
     * @return
     */
    List<PlsAuthority> getAuthorities(Collection<Long> organizations, String resourceTYpe);

    /**
     * 获取资源权限
     *
     * @param organization
     * @param resourceType
     * @param resource
     * @return
     */
    Integer getAuthority(Long organization, String resourceType, Long resource);

}
