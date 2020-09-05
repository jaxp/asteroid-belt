package com.pallas.service.user.cache;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.google.common.base.Joiner;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.cache.cacher.AbstractHashCacher;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/9/4 10:12
 * @desc:
 */
@Component
public class UserInfoCacher extends AbstractHashCacher<String> {

  private static final String USER_INFO_KEY = "pls:user-info-key:";
  private static final String BANNED_KEY = "password";
  private ThreadLocal<Long> context = new ThreadLocal<>();

  @Autowired
  private JwtKeyCacher jwtKeyCacher;
  @Autowired
  private TokenCacher tokenCacher;
  @Autowired
  private IPlsUserService plsUserService;
  @Autowired
  private IPlsAuthorityService plsAuthorityService;

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
    PlsUser user = plsUserService.query()
        .eq("id", context.get())
        .one();
    return toUserMap(user);
  }

  /**
   * 缓存用户
   *
   * @param user
   * @param expireTime
   */
  public String cacheUser(PlsUser user, Long expireTime) {
    // 设置当前上下文
    setContext(user.getId());
    // 清空用户数据
    clear();
    // 缓存用户数据
    cacheData(toUserMap(user));
    // 设置过期时间
    expire(Duration.ofMinutes(expireTime));
    long sid = IdWorker.getId();
    // 缓存token
    tokenCacher.cacheToken(sid, expireTime);
    // 生成token
    String token = Jwts.builder()
        .setExpiration(new Date(System.currentTimeMillis() + expireTime * 60 * 1000))
        .setIssuedAt(new Date())
        .setId(sid + "")
        .setSubject(user.getId() + "")
        .signWith(jwtKeyCacher.getPrivateKey()).compact();
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

  public boolean validate(String token) {
    Jws<Claims> jws = Jwts.parserBuilder()
        .setSigningKey(jwtKeyCacher.getPublicKey())
        .build()
        .parseClaimsJws(token);
    long userId = Long.parseLong(jws.getBody().getSubject());
    long sid = Long.parseLong(jws.getBody().getId());
    setContext(userId);
    tokenCacher.setContext(sid);
    if (tokenCacher.ifExist()) {
      return true;
    }
    return false;
  }
}
