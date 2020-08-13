package com.pallas.service.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/8/13 16:52
 * @desc:
 */
@Data
@Accessors(fluent = true)
public class UserDTO {
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
