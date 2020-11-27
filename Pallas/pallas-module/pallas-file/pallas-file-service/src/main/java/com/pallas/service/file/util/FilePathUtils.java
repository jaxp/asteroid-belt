package com.pallas.service.file.util;

import com.google.common.base.Strings;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.base.api.response.ResultType;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author: jax
 * @time: 2020/11/26 15:18
 * @desc:
 */
public class FilePathUtils {

    public static final String getPath(String module, String extension) {
        try {
            String root = new File(ResourceUtils.getURL("").getPath()).getAbsolutePath();
            Calendar calendar = Calendar.getInstance();
            StringBuilder path = new StringBuilder(root).append(File.separator)
                .append("files").append(File.separator)
                .append(module).append(File.separator)
                .append(calendar.get(Calendar.YEAR))
                .append(String.format("%02d", (calendar.get(Calendar.MONTH) + 1)))
                .append(String.format("%02d", (calendar.get(Calendar.DAY_OF_MONTH) + 1))).append(File.separator)
                .append(UUID.randomUUID());
            if (!Strings.isNullOrEmpty(extension)) {
                path.append(PlsConstant.DOT).append(extension);
            }
            return path.toString();
        } catch (FileNotFoundException e) {
            throw new PlsException(ResultType.GENERAL_ERR);
        }
    }
}
