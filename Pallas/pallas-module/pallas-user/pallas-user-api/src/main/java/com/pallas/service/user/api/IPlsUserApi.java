package com.pallas.service.user.api;

import com.pallas.service.user.dto.PlsUserDTO;

/**
 * @author: jax
 * @time: 2020/8/14 14:17
 * @desc: 用户服务接口
 */
public interface IPlsUserApi {

    /**
     * 获取当前用户
     *
     * @return
     */
    PlsUserDTO getCurrent();

    /**
     * 获取用户
     *
     * @param username
     * @return
     */
    PlsUserDTO getUser(String username);

}
