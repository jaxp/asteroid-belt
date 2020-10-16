package com.pallas.service.user.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author: jax
 * @time: 2020/8/13 16:50
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_u_user")
public class PlsUser implements UserDetails {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 新增时间
     */
    private Date addTime;
    /**
     * 更新时间
     */
    private Date updTime;
    /**
     * 是否有效
     */
    private Boolean enabled;
    /**
     * 账号过期
     */
    private Boolean accountExpired;
    /**
     * 账号锁定
     */
    private Boolean accountLocked;
    /**
     * 密码过期
     */
    private Boolean pwdExpired;
    /**
     * 权限
     */
    @JsonIgnore
    @TableField(exist = false)
    private List<PlsAuthority> authorities;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return !this.pwdExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
