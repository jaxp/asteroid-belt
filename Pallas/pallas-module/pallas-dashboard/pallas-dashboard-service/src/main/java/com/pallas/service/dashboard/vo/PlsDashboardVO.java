package com.pallas.service.dashboard.vo;

import com.pallas.service.dashboard.api.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author: jax
 * @time: 2020/12/16 13:59
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsDashboardVO {
    private Long id;
    private String label;
    private Integer sort;
    private String name;
    private String height;
    private String width;
    private Type type;
    private Long templateId;
    private String content;
    private Long addUser;
    private Long updUser;
    private Date addTime;
    private Date updTime;

    private Integer permission;
    private PlsDashboardTemplateVO template;
    private List<PlsDashboardDataVO> data;
}
