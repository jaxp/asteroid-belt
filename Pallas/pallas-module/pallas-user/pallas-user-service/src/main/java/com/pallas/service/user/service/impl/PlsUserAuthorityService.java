package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsUserAuthority;
import com.pallas.service.user.mapper.PlsUserAuthorityMapper;
import com.pallas.service.user.service.IPlsUserAuthorityService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/8/26 11:26
 * @desc:
 */
@Service
public class PlsUserAuthorityService extends ServiceImpl<PlsUserAuthorityMapper, PlsUserAuthority> implements IPlsUserAuthorityService {
}
