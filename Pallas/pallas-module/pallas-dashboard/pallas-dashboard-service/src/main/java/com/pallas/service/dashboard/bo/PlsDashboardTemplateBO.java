package com.pallas.service.dashboard.bo;

import com.pallas.service.dashboard.api.enums.Type;
import com.pallas.service.dashboard.config.DashboardDataManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author: jax
 * @time: 2020/12/11 11:00
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsDashboardTemplateBO extends DashboardDataManager {
    private Long id;
    private Type type;
    private String name;
    private String content;
    private String remark;
    private Long addUser;
    private Long updUser;
    private Date addTime;
    private Date updTime;

    private List<PlsDashboardDataBO> data;

    public void initData() {
        this.data = dataService().getData(this.id);
    }
}
