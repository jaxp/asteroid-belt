package com.pallas.service.file.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pallas.service.file.enums.Sensibility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author: jax
 * @time: 2020/11/26 9:51
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("pls_f_info")
public class PlsFileInfo {
    @TableId
    private Long id;
    /**
     * 原始名称
     */
    private String originName;
    /**
     * 文件扩展名
     */
    private String extension;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 文件md5
     */
    private String md5;
    /**
     * 文件模块
     */
    private String module;
    /**
     * 路径
     */
    private String path;
    /**
     * 是否有归属人
     */
    private Sensibility sensibility;
    /**
     * 上传时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;
    /**
     * 上传人
     */
    private Long addUser;
    /**
     * 剩余下载次数
     */
    private Integer restTimes;
    /**
     * 过期时间
     */
    private Date expireTime;
}
