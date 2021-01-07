package com.pallas.service.dashboard.converter;

import com.pallas.common.converter.CommonConverter;
import com.pallas.service.dashboard.bean.PlsDashboardTemplate;
import com.pallas.service.dashboard.bo.PlsDashboardTemplateBO;
import com.pallas.service.dashboard.vo.PlsDashboardTemplateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/12/11 11:20
 * @desc:
 */
@Component
public class DashboardTemplateConverter extends CommonConverter<PlsDashboardTemplate, PlsDashboardTemplateBO, PlsDashboardTemplateVO> {

    @Autowired
    private DashboardDataConverter dataConverter;

    @Override
    public PlsDashboardTemplateVO bo2vo(PlsDashboardTemplateBO bo) {
        if (Objects.isNull(bo)) {
            return null;
        }
        PlsDashboardTemplateVO vo = super.bo2vo(bo);
        vo.setData(dataConverter.bo2vo(bo.getData()));
        return vo;
    }
}
