package com.pallas.service.dashboard.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: jax
 * @time: 2020/12/15 15:38
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_d_dashboard_data")
public class PlsDashboardData {
    @TableId
    private Long id;
    /**
     * 模板编号
     */
    private Long templateId;
    /**
     * 取值后的key
     */
    private String label;
    /**
     * 请求uri
     */
    private String uri;
    /**
     * 请求参数
     */
    private String params;
}
