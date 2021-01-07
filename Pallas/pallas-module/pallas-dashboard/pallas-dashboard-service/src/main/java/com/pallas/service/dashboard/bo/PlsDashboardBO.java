package com.pallas.service.dashboard.bo;

import com.pallas.service.dashboard.api.enums.Type;
import com.pallas.service.dashboard.config.DashboardDataManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: jax
 * @time: 2020/12/16 9:24
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsDashboardBO extends DashboardDataManager {
    private Long id;
    private String label;
    private Integer sort;
    private String name;
    private String height;
    private String width;
    private Long templateId;
    private Type type;
    private String content;
    private Long addUser;
    private Long updUser;
    private Date addTime;
    private Date updTime;

    private Integer permission;
    private PlsDashboardTemplateBO template;
    private List<PlsDashboardDataBO> data;

    public PlsDashboardTemplateBO getTemplate() {
        if (Objects.isNull(templateId)) {
            return null;
        }
        if (Objects.nonNull(template)) {
            return template;
        }
        this.template = templateService().getTemplate(templateId);
        return template;
    }

}
