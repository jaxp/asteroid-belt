package com.pallas.service.user.cache;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.cache.cacher.AbstractHashCacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/9/4 13:39
 * @desc:
 */
@Component
public class TokenCacher extends AbstractHashCacher<String> {

    private static final String TOKEN_KEY = "pls:token:";
    private ThreadLocal<Long> context = new ThreadLocal<>();

    @Autowired
    private UserCacher userCacher;

    public void setContext(long id) {
        context.set(id);
    }

    public Long getContext() {
        return context.get();
    }

    @Override
    public String getKey() {
        return new StringBuilder(TOKEN_KEY)
            .append(userCacher.getContext())
            .append(PlsConstant.COLON)
            .append(getContext())
            .toString();
    }

    public Set<String> tokenKeysOfSameUser() {
        return this.getTemplate().keys(new StringBuilder(TOKEN_KEY)
            .append(userCacher.getContext())
            .append(PlsConstant.COLON)
            .append(PlsConstant.ASTERISK)
            .toString());
    }

    @Override
    public Map<String, String> loadData() {
        return null;
    }

    public Map<String, String> buildToken(long sid, String token, long expireTime) {
        setContext(sid);
        Map<String, String> data = new HashMap<>(6);
        long now = System.currentTimeMillis();
        data.put("time", now + "");
        data.put("expire", now + expireTime * 60 * 1000 + "");
        data.put("token", token);
        return data;
    }

}
