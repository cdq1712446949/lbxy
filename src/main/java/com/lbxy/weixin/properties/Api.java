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
    public static final String LOGIN = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    /** 需要参数 appID 和 appSecret */
    public static final String ACCESS_TOKEN_URI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * 查询订单接口
     */
    public static final String ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 发送模板消息的接口
     */
    public static final String SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";

}
