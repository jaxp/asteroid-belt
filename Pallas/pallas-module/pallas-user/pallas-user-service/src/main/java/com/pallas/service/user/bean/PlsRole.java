package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/10/16 15:37
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_role")
public class PlsRole {
    @TableId
    private Long id;
    /**
     * 父角色
     */
    private Long pid;
    /**
     * 角色
     */
    private String role;
    /**
     * 名称
     */
    private String name;
    /**
     * 等级
     */
    private Integer grade;
    /**
     * 是否可用
     */
    private Boolean enabled;
    /**
     * 新增时间
     */
    private Date addTime;
    /**
     * 更新时间
     */
    private Date updTime;
}
