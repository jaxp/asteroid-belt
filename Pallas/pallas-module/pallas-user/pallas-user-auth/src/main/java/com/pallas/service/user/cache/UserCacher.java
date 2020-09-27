package com.pallas.service.user.cache;

import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.cache.cacher.AbstractHashCacher;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PublicKey;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/9/4 10:12
 * @desc:
 */
public class UserCacher extends AbstractHashCacher<String> {

    private static final String USER_INFO_KEY = "pls:user-info-key:";
    private ThreadLocal<Long> context = new ThreadLocal<>();

    @Autowired
    protected JwtKeyCacher jwtKeyCacher;
    @Autowired
    protected TokenCacher tokenCacher;

    public void setContext(long id) {
        context.set(id);
    }

    public Long getContext() {
        return context.get();
    }

    @Override
    public String getKey() {
        if (Objects.isNull(context.get())) {
            throw new PlsException(ResultType.GENERAL_ERR, "用户上下文未设置");
        }
        return USER_INFO_KEY + context.get();
    }

    @Override
    public Map<String, String> loadData() {
        return null;
    }

    public Boolean validate(String token) {
        PublicKey publicKey = jwtKeyCacher.getPublicKey();
        Jws<Claims> jws = Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token);
        long userId = Long.parseLong(jws.getBody().getSubject());
        long sid = Long.parseLong(jws.getBody().getId());
        setContext(userId);
        tokenCacher.setContext(sid);
        return tokenCacher.ifExist();
    }
}
