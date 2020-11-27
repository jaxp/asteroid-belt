package com.pallas.service.file.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/11/26 9:05
 * @desc: 文件信息
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlsFileInfoDTO {

    private Long id;
    private String originName;
    private String extension;
    private Long fileSize;
    private String md5;
    private String module;
    private String path;
    private Boolean sensitive;
    private Date addTime;
    private Long addUser;
    private Integer restTimes;
    private Date expireTime;

}
