package com.pallas.base.api.exception;


import com.pallas.base.api.response.ResultType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: jax
 * @time: 2020/8/14 10:34
 * @desc: 通用异常类
 */
@Data
@AllArgsConstructor
public class PlsException extends RuntimeException {

    private int code;

    public PlsException(String msg) {
        super(msg);
        this.code = ResultType.GENERAL_ERR.getCode();
    }

    public PlsException(Throwable cause) {
        super(cause);
        this.code = ResultType.GENERAL_ERR.getCode();
    }

    public PlsException(String msg, Throwable cause) {
        super(msg, cause);
        this.code = ResultType.GENERAL_ERR.getCode();
    }

    public PlsException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public PlsException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public PlsException(ResultType resultType) {
        super(resultType.getMsg());
        this.code = resultType.getCode();
    }

    public PlsException(ResultType resultType, String msg) {
        super(msg);
        this.code = resultType.getCode();
    }

    public PlsException(ResultType resultType, String msg, Throwable cause) {
        super(msg, cause);
        this.code = resultType.getCode();
    }

    public PlsException(ResultType resultType, Throwable cause) {
        super(resultType.getMsg(), cause);
        this.code = resultType.getCode();
    }

    @Override
    public String toString() {
        return new StringBuilder("{'code':").append(code)
            .append(",'message':'").append(this.getLocalizedMessage())
            .append("'}").toString();
    }

    public static final PlsException paramMissing(String paramName) {
        return new PlsException(ResultType.PARAM_MISSING, new StringBuilder("参数【").append(paramName).append("】缺失").toString());
    }

    public static final PlsException paramInvalid(String paramName) {
        return new PlsException(ResultType.PARAM_INVALID, new StringBuilder("非法的参数【").append(paramName).append("】").toString());
    }

}
