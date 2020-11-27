package com.pallas.service.file.api;

import com.pallas.service.file.dto.PlsFileUpload;

import java.util.List;

/**
 * @author: jax
 * @time: 2020/11/26 9:03
 * @desc:
 */
public interface IPlsFileApi {

    /**
     * 上传文件
     *
     * @param fileUpload
     * @return
     */
    Long upload(PlsFileUpload fileUpload);

    /**
     * 上传文件
     *
     * @param fileUploads
     * @return
     */
    List<Long> upload(List<PlsFileUpload> fileUploads);

}
