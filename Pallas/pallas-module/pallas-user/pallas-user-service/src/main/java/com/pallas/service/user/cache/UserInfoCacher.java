package com.pallas.service.user.cache;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.service.IPlsAuthorityService;
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
    private static final String DATA = "data";

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
     *
     * @param user
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
        PrivateKey privateKey = rsaKeyCacher.getPrivateKey();
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
        Map<String, String> userMap = new HashMap<>();
        userMap.put(ID, user.getId() + "");
        userMap.put(USERNAME, user.getUsername());
        userMap.put(EMAIL, user.getEmail());
        userMap.put(TELEPHONE, user.getTelephone());
        List<String> authorities = plsAuthorityService.getAuthorities(user.getId());
        try {
            ObjectMapper mapper = new ObjectMapper();
            userMap.put(DATA, mapper.writeValueAsString(user));
            userMap.put(AUTHORITIES, mapper.writeValueAsString(authorities));
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
            if (this.ifExist(AUTHORITIES)) {
                String authoritiesJson = getCache(AUTHORITIES);
                List<String> authorities = objectMapper.readValue(authoritiesJson, List.class);
                plsUserDTO.setAuthorities(authorities);
            }
            return plsUserDTO;
        } catch (JsonProcessingException e) {
            throw new PlsException("用户信息转换失败", e);
        }
    }

}
