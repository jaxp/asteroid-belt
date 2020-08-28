package com.pallas.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/8/26 10:48
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsAuthorityDTO {
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
