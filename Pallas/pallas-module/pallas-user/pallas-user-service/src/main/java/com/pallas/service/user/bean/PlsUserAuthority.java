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
 * @time: 2020/8/26 10:54
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_user_authority")
public class PlsUserAuthority {
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 权限编号
     */
    private Long authorityId;
    /**
     * 添加用户
     */
    private Long addUser;
    /**
     * 新增时间
     */
    private Date addTime;
    /**
     * 更新时间
     */
    private Date updTime;
}
