package com.pallas.service.dashboard.converter;

import com.pallas.common.converter.CommonConverter;
import com.pallas.service.dashboard.bean.PlsDashboard;
import com.pallas.service.dashboard.bo.PlsDashboardBO;
import com.pallas.service.dashboard.vo.PlsDashboardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/12/16 14:41
 * @desc:
 */
@Component
public class DashboardConverter extends CommonConverter<PlsDashboard, PlsDashboardBO, PlsDashboardVO> {

    @Autowired
    private DashboardTemplateConverter templateConverter;
    @Autowired
    private DashboardDataConverter dataConverter;

    @Override
    public PlsDashboardVO bo2vo(PlsDashboardBO plsDashboardBO) {
        PlsDashboardVO vo = super.bo2vo(plsDashboardBO);
        vo.setTemplate(templateConverter.bo2vo(plsDashboardBO.getTemplate()));
        vo.setData(dataConverter.bo2vo(plsDashboardBO.getData()));
        return vo;
    }
}
