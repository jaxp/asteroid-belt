package com.pallas.service.captcha.params;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * @author: jax
 * @time: 2020/11/25 22:07
 * @desc:
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SlidingParams {
    private BufferedImage originImage;
    private int width;
    private int height;
    private int startX;
    private int startY;
    private int length;
    private int radius;
    private int[][] data;
    private Map<String, Object> clueData;
}
