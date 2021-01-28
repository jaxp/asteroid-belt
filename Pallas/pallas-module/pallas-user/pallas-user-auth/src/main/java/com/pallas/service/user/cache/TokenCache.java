package com.pallas.service.user.cache;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.cache.cache.AbstractHashCache;
import com.pallas.service.user.properties.AuthProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/9/4 13:39
 * @desc:
 */
@Component
public class TokenCache extends AbstractHashCache<String> {

    private ThreadLocal<String> context = new ThreadLocal<>();

    @Autowired
    private UserCache userCache;
    @Autowired
    protected RsaKeyCache rsaKeyCache;
    @Autowired
    private AuthProperties authProperties;

    public void setContext(String id) {
        context.set(id);
    }

    public String getContext() {
        return context.get();
    }

    @Override
    public String getKey() {
        return new StringBuilder(PlsConstant.TOKEN_KEY)
            .append(userCache.getUserId())
            .append(PlsConstant.COLON)
            .append(getContext())
            .toString();
    }

    public Set<String> tokenKeysOfSameUser() {
        return this.getTemplate().keys(new StringBuilder(PlsConstant.TOKEN_KEY)
            .append(userCache.getUserId())
            .append(PlsConstant.COLON)
            .append(PlsConstant.ASTERISK)
            .toString());
    }

    @Override
    public Map<String, String> loadData() {
        return null;
    }

    public String cacheToken(Long userId) {
        long now = System.currentTimeMillis();
        String sid = now + "";
        setContext(sid);
        // 生成token
        PrivateKey privateKey = rsaKeyCache.getPrivateKey();
        long expireTime = authProperties.getExpireTime();
        String token = Jwts.builder()
            .setExpiration(new Date(now + expireTime * PlsConstant.MINUTE))
            .setIssuedAt(new Date())
            .setId(sid + "")
            .setSubject(userId + "")
            .signWith(privateKey)
            .compact();
        Map<String, String> data = new HashMap<>(6);
        data.put("time", now + "");
        data.put("expire", now + authProperties.getExpireTime() * PlsConstant.MINUTE + "");
        data.put("token", token);
        // 缓存token
        cacheData(data);
        // 设置过期时间
        expire(Duration.ofMinutes(expireTime));
        return token;
    }

    public String refreshToken() {
        // 根据刷新时间判断是否刷新token
        long refreshTime = authProperties.getRefreshTime();
        if (Long.parseLong(getData("expire")) < System.currentTimeMillis() + refreshTime * PlsConstant.MINUTE) {
            userCache.expire(Duration.ofMinutes(refreshTime));
            return cacheToken(userCache.getUserId());
        }
        return null;
    }

    public boolean validate(String token) {
        try {
            PublicKey publicKey = rsaKeyCache.getPublicKey();
            Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token);
            String sid = jws.getBody().getId();
            long userId = Long.parseLong(jws.getBody().getSubject());
            userCache.setUserId(userId);
            setContext(sid);
            return ifExist();
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
