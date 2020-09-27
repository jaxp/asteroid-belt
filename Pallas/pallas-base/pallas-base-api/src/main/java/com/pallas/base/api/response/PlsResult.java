package com.pallas.base.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/8/24 15:34
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsResult {
    private Integer code;
    private String msg;
    private Object data;

    public static PlsResult success() {
        return result(ResultType.SUCCESS);
    }

    public static PlsResult success(Object data) {
        return result(ResultType.SUCCESS, data);
    }

    public static PlsResult error(String msg) {
        return new PlsResult().setCode(ResultType.GENERAL_ERR.getCode()).setMsg(msg);
    }

    public static PlsResult error(int code, String msg) {
        return new PlsResult().setCode(code).setMsg(msg);
    }

    public static PlsResult error(ResultType resultType) {
        return new PlsResult().setCode(resultType.getCode()).setMsg(resultType.getMsg());
    }

    public static PlsResult error(ResultType resultType, String msg) {
        return new PlsResult().setCode(resultType.getCode()).setMsg(msg);
    }

    public static PlsResult result(ResultType resultType) {
        return new PlsResult().setCode(resultType.getCode()).setMsg(resultType.getMsg());
    }

    public static PlsResult result(ResultType resultType, Object data) {
        return new PlsResult().setCode(resultType.getCode()).setMsg(resultType.getMsg()).setData(data);
    }
}
