package com.pallas.service.file.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jax
 * @time: 2020/12/8 14:25
 * @desc:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "pls.file.minio")
public class MinioProperties {
    private String url;
    private String accessKey;
    private String secretKey;
}
