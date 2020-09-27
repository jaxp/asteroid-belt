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
    GENERAL_ERR(10000, "请求异常"),
    AUTHORIZATION_ERR(10001, "登录信息异常"),
    ENCRYPTION_ERR(10002, "加解密异常");

    private Integer code;
    private String msg;
}
