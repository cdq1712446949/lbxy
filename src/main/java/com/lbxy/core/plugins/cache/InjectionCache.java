package com.lbxy.core.plugins.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lmy
 * @description InjectionCache
 * @date 2018/8/21
 */
public class InjectionCache {
    private static final Map<String, Object> SERVICE_MAP = new HashMap<>();

    public static Object put(String key, Object value) {
        return SERVICE_MAP.put(key, value);
    }

    public static Object get(String key) {
        return SERVICE_MAP.get(key);
    }
}
