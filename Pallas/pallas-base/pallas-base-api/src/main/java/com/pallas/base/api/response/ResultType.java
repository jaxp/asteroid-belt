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
    ENCRYPTION_ERR(10001, "加解密异常"),
    AUTHORIZATION_EXCEPTION(10100, "登录信息异常"),
    AUTHORIZATION_EXPIRED(10101, "登录信息过期，请重新登录"),
    AUTHORIZATION_INVALID(10102, "无效的登录信息"),
    AUTHORIZATION_INCORRECT(10103, "登录信息错误"),
    AUTHENTICATION_ERR(10200, "用户登录异常"),
    ;

    private Integer code;
    private String msg;
}
