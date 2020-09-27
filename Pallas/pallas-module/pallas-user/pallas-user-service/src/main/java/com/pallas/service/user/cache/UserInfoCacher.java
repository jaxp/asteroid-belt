package com.pallas.service.user.cache;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.base.Joiner;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsUserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.security.PrivateKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/9/7 9:17
 * @desc:
 */
@Component
public class UserInfoCacher extends UserCacher {

    private static final String BANNED_KEY = "password";

    @Autowired
    private IPlsUserService plsUserService;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;

    @Override
    public Map<String, String> loadData() {
        PlsUser user = plsUserService.query()
            .eq("id", getContext())
            .one();
        return toUserMap(user);
    }


    /**
     * 缓存用户
     *  @param user
     * @param expireTime
     * @return
     */
    public String cacheUser(PlsUser user, Long expireTime) {
        // 设置当前上下文
        setContext(user.getId());
        long sid = IdWorker.getId();
        // 清空用户数据
        clear();
        // 缓存用户数据
        cacheData(toUserMap(user));
        tokenCacher.cacheData(tokenCacher.buildToken(sid, expireTime));
        // 设置过期时间
        expire(Duration.ofMinutes(expireTime));
        tokenCacher.expire(Duration.ofMinutes(expireTime));

        // 生成token
        PrivateKey privateKey = jwtKeyCacher.getPrivateKey();
        String token = Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + expireTime * 60 * 1000))
            .setIssuedAt(new Date())
            .setId(sid + "")
            .setSubject(user.getId() + "")
            .signWith(privateKey)
            .compact();
        return token;
    }

    private Map<String, String> toUserMap(PlsUser user) {
        Map<String, String> userMap = null;
        try {
            userMap = toMap(user);
        } catch (IllegalAccessException e) {
            throw new PlsException(ResultType.GENERAL_ERR);
        }
        List<String> authorities = plsAuthorityService.getAuthorities(user.getId());
        userMap.put("authorities", Joiner.on(",").join(authorities));
        return userMap;
    }

    private Map<String, String> toMap(PlsUser user) throws IllegalAccessException {
        Map<String, String> userMap = new HashMap<>();
        Field[] fields = PlsUser.class.getDeclaredFields();
        for (Field field : fields) {
            if (Objects.equals(field.getName(), BANNED_KEY)) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(user);
            field.setAccessible(false);
            if (Objects.isNull(value)) {
                continue;
            }
            if (value instanceof Boolean) {
                value = (boolean) value ? 1 : 0;
            } else if (value instanceof Date) {
                value = ((Date) value).getTime();
            }
            userMap.put(field.getName(), value + "");
        }
        return userMap;
    }

}
