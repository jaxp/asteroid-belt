package com.pallas.service.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.user.bean.PlsAuthority;

import java.util.List;

/**
 * @author: jax
 * @time: 2020/8/26 11:21
 * @desc:
 */
public interface IPlsAuthorityService extends IService<PlsAuthority> {

    /**
     * 获取权限
     *
     * @param userId
     * @return
     */
    List<String> getAuthorities(long userId);

    /**
     * 获取用户权限
     *
     * @param userId
     * @return
     */
    List<PlsAuthority> authorities(long userId);
}
