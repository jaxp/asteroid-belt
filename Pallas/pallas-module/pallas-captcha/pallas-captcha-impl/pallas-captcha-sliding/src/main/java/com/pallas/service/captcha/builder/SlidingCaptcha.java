package com.pallas.service.captcha.builder;

import com.google.common.base.Strings;
import com.pallas.base.api.constant.PlsConstant;
import com.pallas.base.api.exception.PlsException;
import com.pallas.service.captcha.entity.CaptchaClue;
import com.pallas.service.captcha.entity.CaptchaImage;
import com.pallas.service.captcha.entity.CaptchaResult;
import com.pallas.service.captcha.handler.ICaptchaGear;
import com.pallas.service.captcha.params.SlidingParams;
import com.pallas.service.captcha.utils.GaussianUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author: jax
 * @time: 2020/11/25 21:54
 * @desc: 滑动验证码
 */
@Slf4j
public class SlidingCaptcha extends AbstractCaptcha {
    // 方块与整个图的宽度比例
    private static final double LENGTH = 0.15;
    // 缺口与边长的比例
    private static final double RADIUS = 0.35;
    // 方块外围白色边框与边长的比例
    private static final double BORDER = 0.1;
    // 范围误差
    private static final int GAP = 3;
    // 底部图片
    private static final String BASE_NAME = "base.png";
    // 方块图片
    private static final String CUTOUT_NAME = "cutout.png";
    // 最终图片长度
    private static final int IMG_WIDTH = 200;
    // 最终图片高度
    private static final int IMG_HEIGHT = 100;

    @Override
    public CaptchaResult init() {
        try {
            SlidingParams param = this.loadImage();
            // 生成方块
            this.generateBlock(param);
            // 切割图片
            List<CaptchaImage> images = this.cutImage(param);
            // 线索
            CaptchaClue clue = new CaptchaClue()
                .setCid(getCid())
                .setData(param.getClueData());
            // 验证码结果
            String result = new StringBuilder(param.getStartX() + "").append(PlsConstant.COLON)
                .append(param.getWidth()).append(PlsConstant.COLON)
                .append(UUID.randomUUID()).toString();
            // 验证码
            return new CaptchaResult(clue, images, result);
        } catch (Exception e) {
            throw new PlsException("滑动验证码初始化失败", e);
        }
    }

    @Override
    public void expire(String cid) {
        super.expire(cid);
    }

    @Override
    public String check(String cid, String checkCode, boolean enable) {
        String value = captchaHandler().getCache(cid);
        if (Strings.isNullOrEmpty(value)) {
            return "";
        }
        try {
            String[] valueArr = value.split(PlsConstant.COLON);
            String[] codeArr = checkCode.split(PlsConstant.COLON);
            int c = Integer.parseInt(valueArr[0]);
            int t = Integer.parseInt(valueArr[1]);
            int cc = Integer.parseInt(codeArr[0]);
            int ct = Integer.parseInt(codeArr[1]);
            if (!enable || Math.abs(c * ct / t - cc) < GAP) {
                captchaHandler().cacheCertificate(valueArr[2]);
                return valueArr[2];
            }
        } catch (Exception e) {
        } finally {
            captchaHandler().expire(cid);
        }
        return "";
    }

    private SlidingParams loadImage() throws IOException {
        File parent = new File(ResourceUtils.getURL("img").getPath());
        File[] files = parent.listFiles();
        if (files.length == 0) {
            throw new RuntimeException("验证码图片模板未设置");
        }
        int random = new Double(Math.random() * files.length).intValue();
        File originFile = files[random];
        try {
            BufferedImage originImage = ImageIO.read(originFile);
            int width = originImage.getWidth();
            int height = originImage.getHeight();
            return new SlidingParams()
                .setOriginImage(originImage)
                .setWidth(width)
                .setHeight(height);
        } catch (IOException e) {
            throw new PlsException("验证码模板图片未配置", e);
        }
    }

    private void generateBlock(SlidingParams param) {
        int width = param.getWidth();
        int height = param.getHeight();
        int aLength = new Double(height * LENGTH).intValue();
        int aRadius = new Double(aLength * RADIUS).intValue();
        param.setStartX(new Double(width * 0.2 + Math.random() * (width - aLength * 2 - width * 0.3)).intValue());
        param.setStartY(new Double(height * 0.5 - aLength - aRadius).intValue());
        param.setALength(aLength);
        param.setARadius(aRadius);

        int xLen = aLength * 2;
        int yLen = aLength * 2 + aRadius;
        int border = new Double(aLength * BORDER + "").intValue();
        int sa = aRadius * aRadius;
        int ia = (aRadius - border) * (aRadius - border);
        int oa = (aRadius + border) * (aRadius + border);
        int[][] data = new int[xLen][yLen];
        double result;
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                boolean leftBorder = i < border && (j > aRadius && j < aLength || j > aLength + aRadius * 2);
                boolean rightBorder = i > aLength * 2 - border && j > aRadius;
                boolean topBorder = j > aRadius && j < aRadius + border && (i < aLength - aRadius || i > aLength + aRadius);
                boolean bottomBorder = j > aLength * 2 + aRadius - border;
                if (leftBorder || rightBorder || topBorder || bottomBorder) {
                    data[i][j] = 2;
                    continue;
                }
                if (i <= aRadius && j >= aLength && j <= aLength + aRadius * 2) {
                    result = Math.pow(i, 2) + Math.pow(j - aRadius - aLength, 2);
                    if (result < oa) {
                        data[i][j] = result <= sa ? 0 : 2;
                        continue;
                    }
                }
                if (i >= aLength - aRadius && i <= aLength + aRadius && j <= aRadius) {
                    result = Math.pow(i - aLength, 2) + Math.pow(j - aRadius, 2);
                    if (result > ia) {
                        data[i][j] = result > sa ? 0 : 2;
                        continue;
                    }
                }
                //if (i < aRadius && (Math.pow(i, 2) + Math.pow(j - aRadius - aLength, 2) <= pa)
                //    || j < aRadius && (Math.pow(i - aLength, 2) + Math.pow(j - aRadius, 2) > pa)) {
                //  data[i][j] = 0;
                //  continue;
                //}
                if (j > aRadius || i > aLength - aRadius && i < aLength + aRadius) {
                    data[i][j] = 1;
                }
            }
        }
        param.setData(data);
    }

    private List<CaptchaImage> cutImage(SlidingParams param) throws IOException {
        BufferedImage originImage = param.getOriginImage();
        int width = param.getWidth();
        int height = param.getHeight();
        int aLength = param.getALength();
        int aRadius = param.getARadius();
        int startX = param.getStartX();
        int startY = param.getStartY();
        int data[][] = param.getData();
        try {
            int aWidth = aLength * 2;
            int aHeight = aLength * 2 + aRadius;
            BufferedImage baseImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            BufferedImage cutoutImage = new BufferedImage(aWidth, aHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics2D = cutoutImage.createGraphics();
            // 透明背景
            cutoutImage = graphics2D.getDeviceConfiguration().createCompatibleImage(aWidth, aHeight, Transparency.TRANSLUCENT);
            graphics2D = cutoutImage.createGraphics();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int rgb = originImage.getRGB(i, j);
                    if (i >= startX && j >= startY && i < startX + aWidth && j < startY + aHeight) {
                        if (data[i - startX][j - startY] == 1) {
                            cutoutImage.setRGB(i - startX, j - startY, rgb);
                            // 原图对应位置颜色变化
                            int r = (0xff & rgb);
                            int g = (0xff & (rgb >> 8));
                            int b = (0xff & (rgb >> 16));
                            rgb = r + (g << 8) + (b << 16) + (90 << 24);
                        } else if (data[i - startX][j - startY] == 2) {
                            rgb = 0xffffffff;
                            cutoutImage.setRGB(i - startX, j - startY, rgb);
                        }
                    }
                    baseImage.setRGB(i, j, rgb);
                }
            }

            //  设置抗锯齿属性
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setStroke(new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            graphics2D.drawImage(cutoutImage, 0, 0, null);
            graphics2D.dispose();
            // 添加高斯模糊
            baseImage = GaussianUtils.simpleBlur(baseImage);
            cutoutImage = GaussianUtils.simpleBlur(cutoutImage);
            // 改变图片大小
            float definition = 1.5f;
            ICaptchaGear captchaGear = captchaGear();
            if (Objects.nonNull(captchaGear)) {
                definition = captchaGear.definition();
            }
            int realWidth = new Float(IMG_WIDTH * definition).intValue();
            int realHeight = new Float(IMG_HEIGHT * definition).intValue();
            BufferedImage blurBaseImage = new BufferedImage(realWidth, realHeight, baseImage.getType());
            Graphics blurBaseGraphics = blurBaseImage.getGraphics();
            blurBaseGraphics.drawImage(baseImage.getScaledInstance(realWidth, realHeight, Image.SCALE_SMOOTH), 0, 0, null);
            blurBaseGraphics.dispose();
            // 同比例压缩
            aWidth = aWidth * realWidth / width;
            aHeight = aHeight * realHeight / height;
            BufferedImage blurCutoutImage = new BufferedImage(aWidth, aHeight, cutoutImage.getType());
            Graphics blurCutoutGraphics = blurCutoutImage.getGraphics();
            blurCutoutGraphics.drawImage(cutoutImage.getScaledInstance(aWidth, aHeight, Image.SCALE_SMOOTH), 0, 0, null);
            blurCutoutGraphics.dispose();
            // 保存图片
            List<CaptchaImage> images = new ArrayList<>();
            try (ByteArrayOutputStream baseOs = new ByteArrayOutputStream();
                 ByteArrayOutputStream cutoutOs = new ByteArrayOutputStream()
            ) {
                ImageIO.write(blurBaseImage, "png", baseOs);
                images.add(new CaptchaImage(baseOs.toByteArray(), BASE_NAME));
                ImageIO.write(blurCutoutImage, "png", cutoutOs);
                images.add(new CaptchaImage(cutoutOs.toByteArray(), CUTOUT_NAME));
            }
            //CompressUtils.fromBufferedImage2(blurBaseImage, "jpg");
            Map<String, Object> clueData = new HashMap<>(2);
            clueData.put("rate", LENGTH);
            clueData.put("y", 0.5 - LENGTH - LENGTH * RADIUS);
            param.setClueData(clueData);
            return images;
        } catch (Exception e) {
            throw new PlsException("图片切割失败", e);
        }
    }
}
