package com.pallas.service.user.cache;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.cache.cache.AbstractHashCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/9/4 10:12
 * @desc:
 */
@Slf4j
public class UserCache extends AbstractHashCache<String> {

    private ThreadLocal<Long> context = new ThreadLocal<>();

    @Autowired
    protected TokenCache tokenCache;

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

}
