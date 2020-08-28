package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthority;
import com.pallas.service.user.mapper.PlsAuthorityMapper;
import com.pallas.service.user.service.IPlsAuthorityService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/8/26 11:22
 * @desc:
 */
@Service
public class PlsAuthorityService extends ServiceImpl<PlsAuthorityMapper, PlsAuthority> implements IPlsAuthorityService {
}
