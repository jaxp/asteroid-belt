package com.pallas.service.user.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/8/13 16:50
 * @desc:
 */
@Data
@Accessors(fluent = true)
public class User {

  /**
   * 编号
   */
  private Long id;
  /**
   * 用户名
   */
  private String username;
  /**
   * 密码
   */
  private String password;

}
