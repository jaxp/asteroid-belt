package com.pallas.service.dashboard.converter;

import com.pallas.common.converter.CommonConverter;
import com.pallas.service.dashboard.bean.PlsDashboardData;
import com.pallas.service.dashboard.bo.PlsDashboardDataBO;
import com.pallas.service.dashboard.vo.PlsDashboardDataVO;
import org.springframework.stereotype.Component;

/**
 * @author: jax
 * @time: 2020/12/16 14:49
 * @desc:
 */
@Component
public class DashboardDataConverter extends CommonConverter<PlsDashboardData, PlsDashboardDataBO, PlsDashboardDataVO> {
}
