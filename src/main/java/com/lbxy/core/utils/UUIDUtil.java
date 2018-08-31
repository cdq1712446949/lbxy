package com.lbxy.core.utils;

import java.util.UUID;

/**
 * @author lmy
 * @description UUIDUtil
 * @date 2018/8/31
 */
public class UUIDUtil {
    public static String generateUUID32() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
