package com.pallas.service.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.service.file.bean.PlsFileInfo;
import com.pallas.service.file.dto.PlsFileUpload;
import com.pallas.service.file.mapper.PlsFileInfoMapper;
import com.pallas.service.file.service.IPlsFileInfoService;
import com.pallas.service.file.util.FilePathUtils;
import com.pallas.service.user.api.IPlsUserApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/11/26 14:00
 * @desc:
 */
@Slf4j
@Service
public class PlsFileInfoService extends ServiceImpl<PlsFileInfoMapper, PlsFileInfo> implements IPlsFileInfoService {

    @Autowired(required = false)
    private IPlsUserApi plsUserClient;

    @Override
    public Long upload(PlsFileUpload fileUpload) {
        return upload(Arrays.asList(fileUpload)).get(0);
    }

    @Override
    public List<Long> upload(List<PlsFileUpload> fileUploads) {
        Long addUser = Objects.nonNull(plsUserClient) ? plsUserClient.getCurrent().getId() : null;
        List<PlsFileInfo> infos = fileUploads.stream()
            .map(e -> {
                String fileName = e.getFileName();
                String extension = "";
                if (fileName.indexOf(PlsConstant.DOT) > -1) {
                    extension = fileName.substring(fileName.lastIndexOf(PlsConstant.DOT) + 1);
                }
                return new PlsFileInfo()
                    .setModule(e.getModule())
                    .setMd5(DigestUtils.md5Hex(e.getContent()).toUpperCase())
                    .setFileSize(e.getFileSize())
                    .setExtension(extension)
                    .setOriginName(fileName)
                    .setPath(FilePathUtils.getPath(e.getModule(), extension))
                    .setSensitive(e.getSensitive())
                    .setExpireTime(e.getExpireTime())
                    .setRestTimes(e.getRestTimes())
                    .setAddUser(addUser);
            }).collect(Collectors.toList());
        saveBatch(infos);
        for (int i = 0; i < fileUploads.size(); i++) {
            Path path = Paths.get(infos.get(i).getPath());
            try {
                Files.write(path, fileUploads.get(i).getContent());
            } catch (IOException e) {
                log.error("文件上传异常：【{}】", infos.get(i).getId() + "");
            }
        }
        return null;
    }
}
