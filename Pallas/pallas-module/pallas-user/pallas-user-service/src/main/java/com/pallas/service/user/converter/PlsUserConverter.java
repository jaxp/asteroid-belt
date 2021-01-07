package com.pallas.service.user.converter;

import com.pallas.common.converter.CloudConverter;
import com.pallas.service.user.bean.PlsUser;
import com.pallas.service.user.bo.PlsUserBO;
import com.pallas.service.user.dto.PlsUserDTO;
import com.pallas.service.user.vo.PlsUserVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/8/24 15:08
 * @desc:
 */
@Component
public class PlsUserConverter extends CloudConverter<PlsUser, PlsUserBO, PlsUserDTO, PlsUserVO> {

    @Override
    public PlsUserBO do2bo(PlsUser d) {
        PlsUserBO bo = super.do2bo(d);
        List<String> authorities = d.getAuthorities().stream()
            .map(e -> e.getAuthority())
            .collect(Collectors.toList());
        bo.setAuthorities(authorities);
        return bo;
    }
}
