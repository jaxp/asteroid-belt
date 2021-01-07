package com.pallas.service.dashboard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/12/16 13:59
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsDashboardDataVO {
    private Long id;
    private Long templateId;
    private String label;
    private String uri;
    private String params;
}
