package com.pallas.server.cloud.file.controller;

import com.pallas.service.file.api.IPlsFileApi;
import com.pallas.service.file.dto.PlsFileUpload;
import com.pallas.service.file.service.IPlsFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: jax
 * @time: 2020/11/26 16:06
 * @desc:
 */
@RestController
@RequestMapping("/api/cloud/file")
public class FileCloudController implements IPlsFileApi {

    @Autowired
    private IPlsFileInfoService plsFileInfoService;

    @Override
    @PostMapping("/upload")
    public Long upload(PlsFileUpload fileUpload) {
        return plsFileInfoService.upload(fileUpload);
    }

    @Override
    @PostMapping("/uploadMulti")
    public List<Long> upload(List<PlsFileUpload> fileUploads) {
        return plsFileInfoService.upload(fileUploads);
    }
}
