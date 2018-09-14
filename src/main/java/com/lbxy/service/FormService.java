package com.lbxy.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.ehcache.CacheKit;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author lmy
 * @description FormService
 * @date 2018/9/11
 */
public interface FormService {

    /**
     * 往EhCache中存放FormId，每个userId建一个cache，其中的formid的key按照顺序增长
     *
     * @param userId
     * @param formId
     * @param expireSeconds
     */
    static void put(long userId, String formId, int expireSeconds) {
        CacheManager ehCacheManager = CacheKit.getCacheManager();
        Cache currentCache;
        if (!ehCacheManager.cacheExists(String.valueOf(userId))) {
            ehCacheManager.addCache(String.valueOf(userId));
        }
        currentCache = ehCacheManager.getCache(String.valueOf(userId));
        List keys = currentCache.getKeysWithExpiryCheck();
        Element element;
        if (keys.isEmpty()) {
            element = new Element(1, formId, 0, expireSeconds);
        } else {
            int count = keys.size();
            element = new Element(count, formId, 0, expireSeconds);
        }
        currentCache.put(element);
    }

    /**
     * 根据userId随机获取一个formId
     *
     * @param userId
     * @return
     */
    static Optional<String> getRandom(long userId) {
        CacheManager ehCacheManager = CacheKit.getCacheManager();
        if (!ehCacheManager.cacheExists(String.valueOf(userId))) {
            return Optional.empty();
        }
        Cache currentCache = ehCacheManager.getCache(String.valueOf(userId));
        List keys = currentCache.getKeysWithExpiryCheck();
        if (keys.isEmpty()) {
            return Optional.empty();
        } else {
            int size = keys.size();
            int randomKey = RandomUtils.nextInt(1, size + 1);

            return Optional.of(currentCache.get(randomKey).toString());
        }
    }

    void put(long userId, JSONArray formIds);

    String get(long userId) throws Exception;
}
