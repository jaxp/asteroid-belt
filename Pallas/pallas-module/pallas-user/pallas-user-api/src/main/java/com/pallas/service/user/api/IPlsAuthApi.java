package com.pallas.service.user.api;

/**
 * @author: jax
 * @time: 2020/9/4 16:53
 * @desc: 权限api
 */
public interface IPlsAuthApi {
  Long getUser(String token);
}
