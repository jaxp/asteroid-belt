package com.pallas.service.dashboard.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pallas.service.dashboard.api.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/12/15 14:31
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_d_dashboard")
public class PlsDashboard {
    @TableId
    private Long id;
    /**
     * key
     */
    private String label;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 高度
     */
    private String height;
    /**
     * 宽度
     */
    private String width;
    /**
     * 图表类型
     */
    private Type type;
    /**
     * 模板id
     */
    private Long templateId;
    /**
     * 内容
     */
    private String content;
    /**
     * 添加人
     */
    private Long addUser;
    /**
     * 修改人
     */
    private Long updUser;
    /**
     * 添加时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updTime;
}
