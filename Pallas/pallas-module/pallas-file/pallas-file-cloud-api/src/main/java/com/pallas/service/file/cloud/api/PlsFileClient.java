package com.pallas.service.file.cloud.api;

import com.pallas.service.file.api.IPlsFileApi;
import com.pallas.service.file.dto.PlsFileUpload;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: jax
 * @time: 2020/11/26 16:01
 * @desc:
 */
@FeignClient("pallas-cloud-file")
public interface PlsFileClient extends IPlsFileApi {

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/api/file/upload")
    Long upload(@RequestParam PlsFileUpload fileUpload);

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "/api/file/uploadMulti")
    List<Long> upload(@RequestParam List<PlsFileUpload> fileUploads);
}
