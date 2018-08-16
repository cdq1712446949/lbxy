package com.lbxy.weixin.utils;

import com.alibaba.fastjson.JSONObject;
import com.lbxy.core.utils.NetWorkUtil;
import com.lbxy.weixin.exception.WeixinLoginException;
import com.lbxy.weixin.properties.Api;
import com.lbxy.weixin.properties.AuthKey;

/**
 * @author lmy
 * @description WeixinUtil
 * @date 2018/8/14
 */
public class WeixinUtil {
    public static JSONObject login(String code) throws WeixinLoginException {
        String reqUrl = String.format(Api.LOGIN, AuthKey.APP_ID, AuthKey.APP_KEY,code);
        JSONObject result = NetWorkUtil.doGetUri(reqUrl);
        if (result.containsKey("errcode")) {
            throw new WeixinLoginException(result.getString("errcode"));
        }

        return result;
    }
}
