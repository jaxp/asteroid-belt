package com.pallas.service.dashboard.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.service.dashboard.bean.PlsDashboardTemplate;
import com.pallas.service.dashboard.bo.PlsDashboardDataBO;
import com.pallas.service.dashboard.bo.PlsDashboardTemplateBO;
import com.pallas.service.dashboard.converter.DashboardTemplateConverter;
import com.pallas.service.dashboard.mapper.PlsDashboardTemplateMapper;
import com.pallas.service.dashboard.service.IPlsDashboardDataService;
import com.pallas.service.dashboard.service.IPlsDashboardTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/12/11 11:11
 * @desc:
 */
@Service
public class PlsDashboardTemplateService extends ServiceImpl<PlsDashboardTemplateMapper, PlsDashboardTemplate> implements IPlsDashboardTemplateService {

    @Autowired
    private DashboardTemplateConverter dashboardTemplateConverter;
    @Autowired
    private IPlsDashboardDataService plsDashboardDataService;

    @Override
    public PlsDashboardTemplateBO getTemplate(Long id) {
        PlsDashboardTemplate template = getById(id);
        PlsDashboardTemplateBO templateBO = dashboardTemplateConverter.do2bo(template);
        templateBO.initData();
        return templateBO;
    }

    @Override
    public List<PlsDashboardTemplateBO> getTemplates(Set<Long> ids) {
        List<PlsDashboardTemplate> templates = listByIds(ids);
        List<PlsDashboardDataBO> dataList = plsDashboardDataService.getData(ids);
        Map<Long, List<PlsDashboardDataBO>> dataMap = dataList.stream()
            .collect(Collectors.groupingBy(PlsDashboardDataBO::getTemplateId));
        List<PlsDashboardTemplateBO> templateBOs = dashboardTemplateConverter.do2bo(templates);
        templateBOs.forEach(e -> e.setData(dataMap.get(e.getId())));
        return templateBOs;
    }
}
