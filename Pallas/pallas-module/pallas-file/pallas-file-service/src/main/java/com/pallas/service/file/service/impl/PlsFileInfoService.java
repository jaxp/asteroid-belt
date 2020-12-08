package com.pallas.service.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Strings;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.file.bean.PlsFileInfo;
import com.pallas.service.file.constant.ContentType;
import com.pallas.service.file.dto.PlsFileUpload;
import com.pallas.service.file.enums.FileStatus;
import com.pallas.service.file.mapper.PlsFileInfoMapper;
import com.pallas.service.file.service.IPlsFileInfoService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Date;
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

    @Autowired
    private MinioClient minioClient;

    @Override
    public Long upload(PlsFileUpload fileUpload) {
        return upload(Arrays.asList(fileUpload)).get(0);
    }

    @Override
    @Transactional
    public List<Long> upload(List<PlsFileUpload> fileUploads) {
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
                    .setFileSize(new Long(e.getContent().length))
                    .setExtension(extension)
                    .setOriginName(fileName)
                    .setStatus(FileStatus.PREPARED)
                    .setSensibility(e.getSensibility())
                    .setExpireTime(e.getExpireTime())
                    .setRestTimes(e.getRestTimes())
                    .setAddUser(e.getAddUser());
            }).collect(Collectors.toList());
        saveBatch(infos);
        for (int i = 0; i < fileUploads.size(); i++) {
            PlsFileInfo fileInfo = infos.get(i);
            String fileName = Strings.isNullOrEmpty(fileInfo.getExtension()) ? (fileInfo.getId() + "")
                : (fileInfo.getId() + PlsConstant.DOT + fileInfo.getExtension());
            String bucketName = fileInfo.getModule();
            FileStatus status = FileStatus.SUCCESS;
            try {
                if (!minioClient.bucketExists(
                    BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build())
                ) {
                    minioClient.makeBucket(
                        MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
                    );
                }
                byte[] content = fileUploads.get(i).getContent();
                minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .contentType(ContentType.get(fileInfo.getExtension()))
                    .stream(new ByteArrayInputStream(content), content.length, -1)
                    .build());
            } catch (Exception e) {
                status = FileStatus.FAIL;
                log.error("文件上传异常：【{}】", infos.get(i).getId() + "", e);
            } finally {
                update().set("status", status)
                    .eq("id", fileInfo.getId())
                    .update();
            }
        }
        return infos.stream().map(PlsFileInfo::getId).collect(Collectors.toList());
    }

    @Override
    public PlsFileInfo getFile(long id) {
        PlsFileInfo fileInfo = query()
            .eq("id", id)
            .ne("rest_times", 0)
            .ge("expire_time", new Date())
            .one();
        if (Objects.isNull(fileInfo)) {
            throw new PlsException(ResultType.NOT_FOUND, "文件未找到");
        }
        // todo 权限过滤
        return fileInfo;
    }

    @Override
    public void downloadOnce(Long id) {
        update().eq("id", id)
            .gt("rest_times", 0)
            .setSql("rest_times = rest_times - 1")
            .update();
    }
}
