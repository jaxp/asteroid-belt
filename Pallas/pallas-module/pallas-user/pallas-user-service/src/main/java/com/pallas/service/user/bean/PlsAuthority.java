package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/8/26 10:38
 * @desc: 权限
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_authority")
public class PlsAuthority implements GrantedAuthority {

    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 标识
     */
    private String authority;
    /**
     * 等级
     */
    private Integer rank;
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
