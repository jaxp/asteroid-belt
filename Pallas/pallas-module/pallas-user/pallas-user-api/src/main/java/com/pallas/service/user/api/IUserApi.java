package com.pallas.service.user.api;

import com.pallas.service.user.dto.UserDTO;

/**
 * @author: jax
 * @time: 2020/8/14 14:17
 * @desc: 用户服务接口
 */
public interface IUserApi {

  /**
   * 获取用户
   *
   * @return
   */
  UserDTO getUser();

}
