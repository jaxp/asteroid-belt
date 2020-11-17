package com.pallas.service.user.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/11/17 17:38
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsRoleBO {
    private Long id;
    private Long pid;
    private String role;
    private String name;
    private Integer grade;
    private Boolean enabled;
    private Date addTime;
    private Date updTime;
}
