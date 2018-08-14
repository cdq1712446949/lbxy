package com.lbxy.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import com.lbxy.utils.NetWorkUtil;
import com.lbxy.weixin.exception.WeixinLoginException;
import com.lbxy.weixin.properties.Api;
import com.lbxy.weixin.properties.AuthKey;

/**
 * @author lmy
 * @description WeixinUtil
 * @date 2018/8/14
 */
public class WeixinUtil {
    public static JSONObject login(String code) {
        String reqUrl = String.format(Api.LOGIN, AuthKey.APP_ID, AuthKey.APP_KEY);
        JSONObject result = NetWorkUtil.doGetUri(reqUrl);
        if (result.containsKey("errcode")) {
            throw new WeixinLoginException(result.getString("errcode"));
        }

        return result;
    }
}
