package com.pallas.service.user.converter;

import com.pallas.common.converter.CloudConverter;
import com.pallas.service.user.bean.PlsMenu;
import com.pallas.service.user.bo.PlsMenuBO;
import com.pallas.service.user.dto.PlsMenuDTO;
import com.pallas.service.user.vo.PlsMenuVO;
import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/10/21 17:06
 * @desc:
 */
@Component
public class PlsMenuConverter extends CloudConverter<PlsMenu, PlsMenuBO, PlsMenuDTO, PlsMenuVO> {

    @Override
    public PlsMenuVO bo2vo(PlsMenuBO plsMenuBO) {
        PlsMenuVO vo = super.bo2vo(plsMenuBO);
        vo.setType(plsMenuBO.getType().getValue());
        return vo;
    }

}
