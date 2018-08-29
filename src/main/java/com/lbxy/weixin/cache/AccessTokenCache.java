package com.lbxy.weixin.cache;


import com.lbxy.weixin.dto.AccessToken;

/**
 * 微信accessToken缓存类，在servlet中刷新数据
 */
public class AccessTokenCache {
    private static AccessToken accessToken = new AccessToken();

    public static AccessToken getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(AccessToken accessToken) {
        AccessTokenCache.accessToken = accessToken;
    }
}
