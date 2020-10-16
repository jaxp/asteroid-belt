package com.pallas.service.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.user.bean.PlsAuthoritySet;
import com.pallas.service.user.mapper.PlsAuthoritySetMapper;
import com.pallas.service.user.service.IPlsAuthoritySetService;
import org.springframework.stereotype.Service;

/**
 * @author: jax
 * @time: 2020/8/26 11:26
 * @desc:
 */
@Service
public class PlsAuthoritySetService extends ServiceImpl<PlsAuthoritySetMapper, PlsAuthoritySet> implements IPlsAuthoritySetService {
}
