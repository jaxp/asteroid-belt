package com.pallas.server.cloud.gateway.filters;

import com.google.common.base.Strings;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.cache.TokenCache;
import com.pallas.service.user.cache.UserCache;
import com.pallas.service.user.constant.UserConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author: jax
 * @time: 2020/9/4 16:34
 * @desc: 过滤权限
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private static final String BEARER_TOKEN_HEADER = "Authorization";

    @Autowired
    private UserCache userCache;
    @Autowired
    private TokenCache tokenCache;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        try {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String authorization = headers.getFirst(BEARER_TOKEN_HEADER);
            if (StringUtils.isNotBlank(authorization)) {
                if (tokenCache.validate(authorization.substring(BEARER_TOKEN_PREFIX.length()))) {
                    String newToken = tokenCache.refreshToken();
                    if (!Strings.isNullOrEmpty(newToken)) {
                        exchange.getResponse().getHeaders().set(PlsConstant.AUTHORIZATION_HEADER, newToken);
                    }
                    exchange.getRequest().mutate()
                        .header(UserConstant.USER_ID_HEADER, userCache.getUserId() + "")
                        .header(UserConstant.TOKEN_HEADER, tokenCache.getContext() + "")
                        .build();
                    return chain.filter(exchange);
                }
            }
        } catch (PlsException e) {
            throw e;
        } catch (Exception e) {
            throw new PlsException(ResultType.AUTHORIZATION_EXCEPTION, e);
        }
        exchange.getRequest().mutate().header(UserConstant.USER_ID_HEADER, "").build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
