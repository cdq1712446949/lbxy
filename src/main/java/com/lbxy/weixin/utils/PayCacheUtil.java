package com.lbxy.weixin.utils;

import com.jfinal.plugin.ehcache.CacheKit;
import com.lbxy.weixin.properties.Properties;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.Objects;

/**
 * @author lmy
 * @description PayCacheUtil
 * @date 2018/8/31
 */
public class PayCacheUtil {
    private static CacheManager cacheManager = CacheKit.getCacheManager();

    public static void put(String key, String value) {
        cacheManager.getCache(Properties.PAY_CACHE_NAME).put(new Element(key, value));
    }

    public static String get(String key) {
       return cacheManager.getCache(Properties.PAY_CACHE_NAME).get(key).getObjectValue().toString();
    }

    public static String get(String key, String defaultValue) {
        Element element = cacheManager.getCache(Properties.PAY_CACHE_NAME).get(key);
        if (Objects.isNull(element)) {
            return defaultValue;
        } else {
            return element.getObjectValue().toString();
        }
    }
}
