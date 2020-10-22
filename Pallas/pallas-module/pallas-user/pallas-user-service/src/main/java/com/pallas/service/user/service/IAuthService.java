package com.pallas.service.user.service;

import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.dto.PlsMenuDTO;
import com.pallas.service.user.dto.PlsUserDTO;

import java.util.List;

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
     * 获取用户数据
     *
     * @return
     */
    PlsUserDTO getUser();

    /**
     * 获取菜单
     *
     * @return
     */
    List<PlsMenuDTO> getMenus();

    /**
     * 获取权限
     *
     * @return
     */
    List<String> getAuthorities();

    /**
     * 校验token
     *
     * @param token
     * @return
     */
    Boolean validate(String token);

}
