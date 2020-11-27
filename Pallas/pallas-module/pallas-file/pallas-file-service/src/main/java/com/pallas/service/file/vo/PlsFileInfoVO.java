package com.pallas.service.file.vo;

import com.pallas.service.file.enums.Sensibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/11/26 14:06
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsFileInfoVO {
    private Long id;
    private String originName;
    private String extension;
    private Long fileSize;
    private String md5;
    private String module;
    private Sensibility sensibility;
    private Date addTime;
    private Long addUser;
    private Integer restTimes;
    private Date expireTime;
}
