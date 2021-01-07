package com.pallas.service.dashboard.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/12/22 19:33
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DashboardParams {
    private Long id;
    private String name;
    private String label;
    private Integer sort;
    private String height;
    private String width;
    private String content;

    private String templateName;
    private String templateRemark;

}
