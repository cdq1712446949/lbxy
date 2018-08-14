package com.lbxy.weixin.properties;

/**
 * @author lmy
 * @description Api 微信的api
 * @date 2018/8/14
 */
public class Api {
    /**
     * 请求登陆的api 参数分别为 appid，appkey， code
     */
    public static String LOGIN = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
}
