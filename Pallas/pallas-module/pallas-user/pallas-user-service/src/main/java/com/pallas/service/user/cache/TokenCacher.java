package com.pallas.service.user.cache;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.cache.cacher.AbstractHashCacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/9/4 13:39
 * @desc:
 */
@Component
public class TokenCacher extends AbstractHashCacher<String> {

  private static final String TOKEN_KEY = "pls:token-key:";
  private ThreadLocal<Long> context = new ThreadLocal<>();

  @Autowired
  private UserInfoCacher userInfoCacher;

  public void setContext(long id) {
    context.set(id);
  }

  public Long getContext() {
    return context.get();
  }

  @Override
  public String getKey() {
    return new StringBuilder(TOKEN_KEY)
        .append(userInfoCacher.getContext())
        .append(PlsConstant.COLON)
        .append(getContext())
        .toString();
  }

  @Override
  public Map<String, String> loadData() {
    return null;
  }

  public void cacheToken(long sid, Long expireTime) {
    setContext(sid);
    Map<String, String> data = new HashMap<>(6);
    long now = System.currentTimeMillis();
    data.put("time", now + "");
    data.put("expire", now + expireTime * 60 * 1000 + "");
    cacheData(data);
    expire(Duration.ofMinutes(expireTime));
  }
}
