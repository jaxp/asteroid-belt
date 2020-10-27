package com.pallas.service.user.cache;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.cache.cacher.AbstractHashCacher;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.PublicKey;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/9/4 10:12
 * @desc:
 */
@Slf4j
public class UserCacher extends AbstractHashCacher<String> {

    private ThreadLocal<Long> context = new ThreadLocal<>();

    @Autowired
    protected RsaKeyCacher rsaKeyCacher;
    @Autowired
    protected TokenCacher tokenCacher;

    public void setUserId(long id) {
        context.set(id);
    }

    public Long getUserId() {
        return context.get();
    }

    @Override
    public String getKey() {
        if (Objects.isNull(context.get())) {
            throw new PlsException(ResultType.GENERAL_ERR, "用户上下文未设置");
        }
        return PlsConstant.USER_INFO_KEY + context.get();
    }

    @Override
    public Map<String, String> loadData() {
        return null;
    }

    public Boolean validate(String token) {
        try {
            PublicKey publicKey = rsaKeyCacher.getPublicKey();
            Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
            long userId = Long.parseLong(jws.getBody().getSubject());
            long sid = Long.parseLong(jws.getBody().getId());
            setUserId(userId);
            tokenCacher.setContext(sid);
            return tokenCacher.ifExist();
        } catch (ExpiredJwtException e) {
            throw new PlsException(ResultType.AUTHORIZATION_EXPIRED);
        } catch (IncorrectClaimException e) {
            throw new PlsException(ResultType.AUTHORIZATION_INCORRECT);
        } catch (InvalidClaimException e) {
            throw new PlsException(ResultType.AUTHORIZATION_INVALID);
        } catch (JwtException e) {
            throw new PlsException(ResultType.AUTHORIZATION_EXCEPTION, e);
        }
    }
}
