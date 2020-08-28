package com.pallas.base.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: jax
 * @time: 2020/8/24 15:53
 * @desc:
 */
@Getter
@AllArgsConstructor
public enum ResultType {

  SUCCESS(200, "成功"),
  GENERAL_ERR(1, "请求异常");

  private Integer code;
  private String msg;
}
