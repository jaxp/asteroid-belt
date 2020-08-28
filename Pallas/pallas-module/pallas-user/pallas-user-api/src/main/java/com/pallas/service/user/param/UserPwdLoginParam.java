package com.pallas.service.user.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jax
 * @time: 2020/8/25 15:43
 * @desc:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPwdLoginParam {
  private String username;
  private String password;
}
