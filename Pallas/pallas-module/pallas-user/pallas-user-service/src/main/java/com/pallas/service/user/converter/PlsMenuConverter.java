package com.pallas.service.user.converter;

import com.pallas.common.converter.CommonConverter;
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
public class PlsMenuConverter extends CommonConverter<PlsMenu, PlsMenuBO, PlsMenuDTO, PlsMenuVO> {

    @Override
    public PlsMenuVO dto2vo(PlsMenuDTO plsMenuDTO) {
        PlsMenuVO vo = super.dto2vo(plsMenuDTO);
        vo.setType(plsMenuDTO.getType().getValue());
        return vo;
    }
}
