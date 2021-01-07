package com.pallas.service.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.dashboard.bean.PlsDashboardData;
import com.pallas.service.dashboard.bo.PlsDashboardDataBO;
import com.pallas.service.dashboard.converter.DashboardDataConverter;
import com.pallas.service.dashboard.mapper.PlsDashboardDataMapper;
import com.pallas.service.dashboard.service.IPlsDashboardDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author: jax
 * @time: 2020/12/15 16:30
 * @desc:
 */
@Service
public class PlsDashboardDataService extends ServiceImpl<PlsDashboardDataMapper, PlsDashboardData> implements IPlsDashboardDataService {

    @Autowired
    private DashboardDataConverter dataConverter;

    @Override
    public List<PlsDashboardDataBO> getData(Long id) {
        List<PlsDashboardData> data = query()
            .eq("template_id", id)
            .list();
        return dataConverter.do2bo(data);
    }

    @Override
    public List<PlsDashboardDataBO> getData(Set<Long> ids) {
        List<PlsDashboardData> data = query()
            .in("template_id", ids)
            .list();
        return dataConverter.do2bo(data);
    }
}
