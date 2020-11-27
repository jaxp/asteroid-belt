package com.pallas.service.captcha.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

/**
 * @author: jax
 * @time: 2020/11/25 22:27
 * @desc: 文件路径
 */
public class FilePathUtils {
    public static final String getFilePath(String fileName) throws FileNotFoundException {
        long time = Long.parseLong(fileName.substring(fileName.indexOf("_") + 1, fileName.lastIndexOf("_")));
        String root = new File(ResourceUtils.getURL("").getPath()).getAbsolutePath();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        String path = new StringBuilder(root).append(File.separator)
            .append("files").append(File.separator)
            .append(calendar.get(Calendar.YEAR))
            .append(String.format("%02d", (calendar.get(Calendar.MONTH) + 1)))
            .append(String.format("%02d", (calendar.get(Calendar.DAY_OF_MONTH) + 1))).append(File.separator)
            .append(fileName).toString();
        return path;
    }
}
