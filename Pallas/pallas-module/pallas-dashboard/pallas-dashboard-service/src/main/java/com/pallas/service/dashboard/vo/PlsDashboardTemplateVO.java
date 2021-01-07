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
 * @time: 2020/12/11 11:12
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsDashboardTemplateVO {
    private Long id;
    private Type type;
    private String name;
    private String content;
    private String remark;
    private Long addUser;
    private Long updUser;
    private Date addTime;
    private Date updTime;

    private List<PlsDashboardDataVO> data;
}
