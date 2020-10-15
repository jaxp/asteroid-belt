package com.pallas.service.user.cloud.context;

/**
 * @author: jax
 * @time: 2020/10/15 14:20
 * @desc:
 */
public class UserContext {

    private static ThreadLocal<Long> userId = new ThreadLocal<>();
    private static ThreadLocal<Long> tokenId = new ThreadLocal<>();

    public static void setUserId(long id) {
        userId.set(id);
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static void setTokenId(long id) {
        tokenId.set(id);
    }

    public static Long getTokenId() {
        return tokenId.get();
    }

}
