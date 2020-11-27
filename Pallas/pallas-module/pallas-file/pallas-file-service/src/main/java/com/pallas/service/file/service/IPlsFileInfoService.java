package com.pallas.service.file.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pallas.service.file.api.IPlsFileApi;
import com.pallas.service.file.bean.PlsFileInfo;

/**
 * @author: jax
 * @time: 2020/11/26 13:59
 * @desc:
 */
public interface IPlsFileInfoService extends IService<PlsFileInfo>, IPlsFileApi {

    /**
     * 获取文件地址
     *
     * @param id
     * @return
     */
    PlsFileInfo getFile(long id);

    /**
     * 下载一次，剩余下载次数减1
     *
     * @param id
     */
    void downloadOnce(Long id);
}
