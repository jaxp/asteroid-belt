package com.pallas.service.user.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.constant.Permission;
import com.pallas.service.user.constant.ResourceType;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.properties.AuthProperties;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/9/7 9:17
 * @desc:
 */
@Slf4j
@Component
public class UserInfoCache extends UserCache {

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String TELEPHONE = "telephone";
    private static final String DATA = "data";

    @Autowired
    private IPlsUserService plsUserService;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;
    @Autowired
    private AuthProperties authProperties;

    @Override
    public Map<String, String> loadData() {
        PlsUser user = plsUserService.query()
            .eq("id", getUserId())
            .one();
        return toUserMap(user);
    }

    /**
     * 缓存用户
     *
     * @param user
     * @return
     */
    public String cacheUser(PlsUser user) {
        // 设置当前上下文
        setUserId(user.getId());
        // 清空用户数据
        clear();
        // 缓存用户数据
        cacheData(toUserMap(user));
        // 设置过期时间
        expire(Duration.ofMinutes(authProperties.getExpireTime()));
        // 生成token
        return tokenCache.cacheToken(user.getId());
    }

    private Map<String, String> toUserMap(PlsUser user) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put(ID, user.getId() + "");
        userMap.put(USERNAME, user.getUsername());
        userMap.put(EMAIL, user.getEmail());
        userMap.put(TELEPHONE, user.getTelephone());
        Map<String, List<PlsAuthority>> authorityMap = plsAuthorityService.getAuthorityMap(user.getId());
        try {
            ObjectMapper mapper = new ObjectMapper();
            userMap.put(DATA, mapper.writeValueAsString(user));
            for (Map.Entry<String, List<PlsAuthority>> entry : authorityMap.entrySet()) {
                userMap.put(entry.getKey(), mapper.writeValueAsString(
                    entry.getValue().stream()
                        .collect(Collectors.toMap(PlsAuthority::getResource, PlsAuthority::getPermission))
                ));
            }
        } catch (JsonProcessingException e) {
            log.error("缓存用户信息失败", e);
        }
        return userMap;
    }

    public PlsUserDTO getUser() {
        if (!this.ifExist(DATA)) {
            throw new PlsException(ResultType.AUTHORIZATION_EXCEPTION);
        }
        String userJson = this.getCache(DATA);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PlsUser user = objectMapper.readValue(userJson, PlsUser.class);
            PlsUserDTO plsUserDTO = new PlsUserDTO();
            BeanUtils.copyProperties(user, plsUserDTO);
            return plsUserDTO;
        } catch (JsonProcessingException e) {
            throw new PlsException("用户信息转换失败", e);
        }
    }

    public Map<Long, Integer> getAuthorities(String resourceType) {
        Map<Long, Integer> result = this.getInfo(resourceType, new TypeReference<Map<Long, Integer>>() {
        });
        return Optional.ofNullable(result).orElseGet(() -> new HashMap<>());
    }

    public Set<Long> getRoles() {
        Map<Long, Integer> roles = getAuthorities(ResourceType.ROLE);
        return roles.entrySet().stream()
            .filter(e -> !Permission.forbidden(e.getValue()))
            .map(e -> e.getKey())
            .collect(Collectors.toSet());
    }

    private <T> T getInfo(String key, TypeReference<T> reference) {
        String json = this.getCache(key);
        if (Strings.isNullOrEmpty(json)) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            T value = objectMapper.readValue(json, reference);
            return value;
        } catch (JsonProcessingException e) {
            throw new PlsException("系统缓存读取失败", e);
        }
    }

}
