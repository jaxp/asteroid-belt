package com.pallas.service.user.cache;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.service.IPlsAuthorityService;
import com.pallas.service.user.service.IPlsMenuService;
import com.pallas.service.user.service.IPlsRoleService;
import com.pallas.service.user.service.IPlsUserService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/9/7 9:17
 * @desc:
 */
@Slf4j
@Component
public class UserInfoCacher extends UserCacher {

    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String TELEPHONE = "telephone";
    private static final String AUTHORITIES = "authorities";
    private static final String MENUS = "menus";
    private static final String ROLES = "roles";
    private static final String DATA = "data";

    @Autowired
    private IPlsUserService plsUserService;
    @Autowired
    private IPlsRoleService plsRoleService;
    @Autowired
    private IPlsAuthorityService plsAuthorityService;
    @Autowired
    private IPlsMenuService plsMenuService;

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
     * @param expireTime
     * @return
     */
    public String cacheUser(PlsUser user, Long expireTime) {
        // 设置当前上下文
        setUserId(user.getId());
        long sid = IdWorker.getId();
        // 清空用户数据
        clear();
        // 缓存用户数据
        cacheData(toUserMap(user));
        // 生成token
        PrivateKey privateKey = rsaKeyCacher.getPrivateKey();
        String token = Jwts.builder()
            .setExpiration(new Date(System.currentTimeMillis() + expireTime * 60 * 1000))
            .setIssuedAt(new Date())
            .setId(sid + "")
            .setSubject(user.getId() + "")
            .signWith(privateKey)
            .compact();
        tokenCacher.cacheData(tokenCacher.buildToken(sid, token, expireTime));
        // 设置过期时间
        expire(Duration.ofMinutes(expireTime));
        tokenCacher.expire(Duration.ofMinutes(expireTime));
        return token;
    }

    private Map<String, String> toUserMap(PlsUser user) {
        Map<String, String> userMap = new HashMap<>();
        userMap.put(ID, user.getId() + "");
        userMap.put(USERNAME, user.getUsername());
        userMap.put(EMAIL, user.getEmail());
        userMap.put(TELEPHONE, user.getTelephone());
        Set<Long> roles = plsRoleService.getRoles(user.getId());
        List<String> authorities = plsAuthorityService.getAuthorities(user.getId());
        Set<Long> menus = plsMenuService.getMenusIds(user.getId());
        try {
            ObjectMapper mapper = new ObjectMapper();
            userMap.put(DATA, mapper.writeValueAsString(user));
            userMap.put(ROLES, mapper.writeValueAsString(roles));
            userMap.put(AUTHORITIES, mapper.writeValueAsString(authorities));
            userMap.put(MENUS, mapper.writeValueAsString(menus));
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

    public List<String> getAuthorities() {
        return this.getInfo(AUTHORITIES, new TypeReference<List<String>>() {
        });
    }

    public Set<Long> getRoleIds() {
        return this.getInfo(ROLES, new TypeReference<Set<Long>>() {
        });
    }

    public Set<Long> getMenuIds() {
        return this.getInfo(MENUS, new TypeReference<Set<Long>>() {
        });
    }

    private <T> T getInfo(String key, TypeReference<T> reference) {
        String json = this.getCache(key);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            T value = objectMapper.readValue(json, reference);
            return value;
        } catch (JsonProcessingException e) {
            throw new PlsException("系统缓存读取失败", e);
        }
    }

}
