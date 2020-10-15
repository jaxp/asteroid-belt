package com.pallas.service.user.service;

import com.pallas.service.user.bean.PlsUser;

/**
 * @author: jax
 * @time: 2020/9/4 9:47
 * @desc: 登录会话相关
 */
public interface IAuthService {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    String login(PlsUser user);

    /**
     * 退出
     */
    void logout();

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    Boolean validate(String token);

}
