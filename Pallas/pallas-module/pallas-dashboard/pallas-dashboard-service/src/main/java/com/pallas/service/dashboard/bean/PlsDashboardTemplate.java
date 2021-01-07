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
 * @time: 2020/12/11 10:44
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_d_dashboard_template")
public class PlsDashboardTemplate {
    @TableId
    private Long id;
    /**
     * 类型
     */
    private Type type;
    /**
     * 名称
     */
    private String name;
    /**
     * 内容
     */
    private String content;
    /**
     * 备注
     */
    private String remark;
    /**
     * 添加用户
     */
    private Long addUser;
    /**
     * 修改用户
     */
    private Long updUser;
    /**
     * 新增时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updTime;
}
