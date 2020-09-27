package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.cache.UserInfoCacher;
import com.pallas.service.user.converter.PlsUserConverter;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.mapper.PlsUserMapper;
import com.pallas.service.user.service.IPlsUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: jax
 * @time: 2020/8/13 16:53
 * @desc:
 */
@Service
public class PlsUserService extends ServiceImpl<PlsUserMapper, PlsUser> implements IPlsUserService {

    @Autowired
    private PlsUserConverter plsUserConverter;
    @Autowired
    private UserInfoCacher userInfoCacher;

    @Override
    public PlsUserDTO getCurrent(Long userId) {
        userInfoCacher.setContext(userId);
        Map<String, String> userMap = userInfoCacher.getData();
        PlsUserDTO plsUserDTO = new PlsUserDTO();
        BeanUtils.copyProperties(userMap, plsUserDTO);
        return plsUserDTO;
    }

    @Override
    public PlsUserDTO getUser(String username) {
        PlsUser user = query().eq("username", username).one();
        return plsUserConverter.do2dto(user);
    }
}
