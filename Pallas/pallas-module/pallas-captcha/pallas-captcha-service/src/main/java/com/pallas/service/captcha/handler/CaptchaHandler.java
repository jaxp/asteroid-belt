package com.pallas.service.captcha.handler;

import com.pallas.base.api.exception.PlsException;
import com.pallas.service.captcha.builder.AbstractCaptcha;
import com.pallas.service.captcha.builder.ICaptcha;
import com.pallas.service.captcha.constant.CaptchaConstant;
import com.pallas.service.captcha.entity.CaptchaImage;
import com.pallas.service.captcha.entity.CaptchaResult;
import com.pallas.service.file.api.IPlsFileApi;
import com.pallas.service.file.dto.PlsFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: jax
 * @time: 2020/11/27 23:38
 * @desc:
 */
@Component
public class CaptchaHandler implements ICaptchaHandler, ICaptchaGear {

    private static final int EXPIRE = 60;

    @Autowired
    private RedisTemplate<String, String> stringTemplate;
    @Autowired
    private IPlsFileApi plsFileApi;

    @Override
    public boolean enable() {
        return true;
    }

    @Override
    public ICaptcha build(Class<? extends AbstractCaptcha> clazz) {
        try {
            return clazz.newInstance().setCaptchaGear(this).setCaptchaHandler(this);
        } catch (Exception e) {
            throw new PlsException("验证码生成失败");
        }
    }

    @Override
    public String buildId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void saveImages(CaptchaResult captchaResult) {
        List<CaptchaImage> images = captchaResult.getImages();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, EXPIRE);
        List<PlsFileUpload> uploads = images.stream()
            .map(e -> new PlsFileUpload()
                .setModule("captcha")
                .setExpireTime(calendar.getTime())
                .setFileName(e.getFileName())
                .setContent(e.getContent())
                .setRestTimes(1)
            )
            .collect(Collectors.toList());
        List<Long> ids = plsFileApi.upload(uploads);
        captchaResult.getClue().setImages(ids);
    }

    @Override
    public void cache(String cid, String value) {
        stringTemplate.opsForValue().set(CaptchaConstant.KEY_PREFIX + cid, value, EXPIRE, TimeUnit.SECONDS);
    }

    @Override
    public String getCache(String cid) {
        String key = CaptchaConstant.KEY_PREFIX + cid;
        return stringTemplate.hasKey(key) ? stringTemplate.opsForValue().get(key) : null;
    }

    @Override
    public void expire(String cid) {
        stringTemplate.delete(CaptchaConstant.KEY_PREFIX + cid);
    }

    @Override
    public void cacheCertificate(String certificate) {
        stringTemplate.opsForValue().set(CaptchaConstant.CERTIFICATE_PREFIX + certificate, "", EXPIRE, TimeUnit.SECONDS);
    }

    @Override
    public boolean existCertificate(String certificate) {
        return stringTemplate.hasKey(CaptchaConstant.CERTIFICATE_PREFIX + certificate);
    }

    @Override
    public void expireCertificate(String certificate) {
        stringTemplate.delete(CaptchaConstant.CERTIFICATE_PREFIX + certificate);
    }
}
