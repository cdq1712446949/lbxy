package com.lbxy.core.utils;

import java.util.Random;

/**
 * @author lmy
 * @description RandomColorUtil
 * @date 2018/8/29
 */
public class RandomColorUtil {

    public static String generateRandomHex() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        return "#" + r + g + b;
    }

}
