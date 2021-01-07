package com.pallas.service.dashboard.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/12/16 9:27
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsDashboardDataBO {
    private Long id;
    private Long templateId;
    private String label;
    private String uri;
    private String params;
}
