package com.pallas.service.user.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jax
 * @time: 2020/9/4 14:06
 * @desc: 验证相关配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "pls.auth")
public class AuthProperties {
  private Long expireTime;
  private Long refreshTime;
}
