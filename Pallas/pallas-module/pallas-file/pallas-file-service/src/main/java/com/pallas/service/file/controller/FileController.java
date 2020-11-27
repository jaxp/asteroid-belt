package com.pallas.service.file.controller;

import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.PlsResult;
import com.pallas.service.file.dto.PlsFileUpload;
import com.pallas.service.file.enums.Sensitive;
import com.pallas.service.file.params.FileUpload;
import com.pallas.service.file.service.IPlsFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/11/26 14:21
 * @desc:
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private IPlsFileInfoService plsFileInfoService;

    @PostMapping("/upload")
    public PlsResult upload(FileUpload fileUpload) {
        if (Objects.isNull(fileUpload.getFiles()) || fileUpload.getFiles().length == 0) {
            throw PlsException.paramMissing("至少上传一个文件");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, PlsConstant.HALF_HOUR);
        Date expireTime = calendar.getTime();
        List<PlsFileUpload> uploads = Arrays.asList(fileUpload.getFiles()).stream()
            .map(e -> {
                try {
                    return new PlsFileUpload()
                        .setModule(fileUpload.getModule())
                        .setFileSize(e.getSize())
                        .setSensitive(Sensitive.ANOYMOUS)
                        .setFileName(e.getOriginalFilename())
                        .setExpireTime(expireTime)
                        .setContent(e.getBytes());
                } catch (IOException ex) {
                    throw PlsException.paramMissing("文件获取失败");
                }
            })
            .collect(Collectors.toList());
        List<Long> ids = plsFileInfoService.upload(uploads);
        if (ids.size() == 1) {
            return PlsResult.success(ids.get(0));
        }
        return PlsResult.success(ids);
    }

}
