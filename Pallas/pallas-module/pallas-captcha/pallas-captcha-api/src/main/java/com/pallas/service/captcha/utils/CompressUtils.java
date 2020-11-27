package com.pallas.service.captcha.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/**
 * @author: jax
 * @time: 2020/11/25 22:28
 * @desc: 压缩算法
 */
public class CompressUtils {
    public static byte[] fromBufferedImage2(BufferedImage img, String imagType) throws IOException {
        // 得到指定Format图片的writer
        Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(imagType);
        ImageWriter writer = iter.next();

        //ByteOutputStream bos = new ByteOutputStream();
        File outFile = new File("C:\\Users\\jiangfj\\Desktop/c.jpg");
        try (OutputStream out = new FileOutputStream(outFile)) {
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                // 设置压缩质量参数
                param.setCompressionQuality(0.5f);
                param.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
                // 指定压缩时使用的色彩模式
                ColorModel colorModel = img.getColorModel();
                param.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel,
                    colorModel.createCompatibleSampleModel(img.getWidth(), img.getHeight())));
                writer.setOutput(ImageIO.createImageOutputStream(out));
                IIOImage iIamge = new IIOImage(img, null, null);
                writer.write(null, iIamge, param);
                writer.dispose();
            }
            return null;
        }
    }
}
