package com.pallas.service.user.converter;

import com.pallas.common.converter.CloudConverter;
import com.pallas.service.user.bean.PlsRole;
import com.pallas.service.user.bo.PlsRoleBO;
import com.pallas.service.user.dto.PlsRoleDTO;
import com.pallas.service.user.vo.PlsRoleVO;
import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/11/17 19:10
 * @desc:
 */
@Component
public class PlsRoleConverter extends CloudConverter<PlsRole, PlsRoleBO, PlsRoleDTO, PlsRoleVO> {
}
