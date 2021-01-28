package com.pallas.base.api.constant;

/**
 * @author: jax
 * @time: 2020/8/24 13:30
 * @desc:
 */
public class PlsConstant {

    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String COLON = ":";
    public static final String QUOTE = "\"";
    public static final String SEPARATOR = "/";
    public static final String SINGLE_QUOTE = "\"";
    public static final String UNDERLINE = "_";
    public static final String CROSSBAR = "-";
    public static final String DOLLOR = "$";
    public static final String ASTERISK = "*";

    public static final int HALF_HOUR = 30 * 60 * 1000;
    public static final long MINUTE = 60 * 1000;

    public static final String RESULT_TYPE_HEADER = "result-type";
    public static final String AUTHORIZATION_HEADER = "authorization";
    /**
     * rsa对应的缓存键
     */
    public static final String RSA_CACHE_KEY = "pls:rsa-key";
    /**
     * token对应的缓存键
     */
    public static final String TOKEN_KEY = "pls:token:";
    /**
     * 用户信息对应的缓存键
     */
    public static final String USER_INFO_KEY = "pls:user-info:";
    /**
     * 角色对应的缓存key
     */
    public static final String ROLE_INFO_KEY ="pls:role-info:";

}
