package com.pallas.service.file.controller;

import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.PlsResult;
import com.pallas.base.api.response.ResultType;
import com.pallas.service.file.bean.PlsFileInfo;
import com.pallas.service.file.constant.FileConstant;
import com.pallas.service.file.dto.PlsFileUpload;
import com.pallas.service.file.enums.Sensibility;
import com.pallas.service.file.params.FileUpload;
import com.pallas.service.file.service.IPlsFileInfoService;
import com.pallas.service.user.api.IPlsUserApi;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
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

    @Autowired(required = false)
    private IPlsUserApi plsUserClient;
    @Autowired
    private IPlsFileInfoService plsFileInfoService;
    @Autowired
    private MinioClient minioClient;

    @PostMapping("/upload")
    public PlsResult upload(FileUpload fileUpload) {
        if (Objects.isNull(fileUpload.getFiles()) || fileUpload.getFiles().length == 0) {
            throw new PlsException(ResultType.PARAM_MISSING, "至少上传一个文件");
        }
        Long addUser = Objects.nonNull(plsUserClient) ? plsUserClient.getCurrent().getId() : null;

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, PlsConstant.HALF_HOUR);
        Date expireTime = calendar.getTime();
        List<PlsFileUpload> uploads = Arrays.asList(fileUpload.getFiles()).stream()
            .map(e -> {
                try {
                    return new PlsFileUpload()
                        .setModule(fileUpload.getModule())
                        .setAddUser(addUser)
                        .setSensibility(Sensibility.ANOYMOUS)
                        .setFileName(e.getOriginalFilename())
                        .setExpireTime(expireTime)
                        .setContent(e.getBytes());
                } catch (IOException ex) {
                    throw new PlsException("文件读取失败");
                }
            })
            .collect(Collectors.toList());
        List<Long> ids = plsFileInfoService.upload(uploads);
        if (ids.size() == 1) {
            return PlsResult.success(ids.get(0));
        }
        return PlsResult.success(ids);
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PlsFileInfo fileInfo = plsFileInfoService.getFile(id);
        String bucketName = fileInfo.getModule();
        String fileName = Strings.isNullOrEmpty(fileInfo.getExtension()) ? (fileInfo.getId() + "")
            : (fileInfo.getId() + PlsConstant.DOT + fileInfo.getExtension());
        boolean successful = true;
        StatObjectResponse stat = minioClient.statObject(StatObjectArgs.builder()
            .bucket(bucketName)
            .object(fileName)
            .build());
        response.setContentType(stat.contentType());
        // 浏览器兼容
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT).toLowerCase();
        String showName = null;
        if (userAgent.contains(FileConstant.AGENT_MSIE) || userAgent.contains(FileConstant.AGENT_GECKO)) {
            showName = URLEncoder.encode(fileInfo.getOriginName(), Charsets.UTF_8.name());
        } else {
            showName = new String(fileInfo.getOriginName().getBytes(Charsets.UTF_8.name()), Charsets.ISO_8859_1.name());
        }
        response.addHeader(HttpHeaders.CONTENT_DISPOSITION, FileConstant.CONTENT_DISPOSITION_PREFIX + showName);
        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        try (
            GetObjectResponse in = minioClient.getObject(
                GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build())
        ) {
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            successful = false;
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            throw new PlsException("文件下载失败", e);
        } finally {
            if (successful && fileInfo.getRestTimes() > 0) {
                plsFileInfoService.downloadOnce(fileInfo.getId());
            }
        }
    }

}
