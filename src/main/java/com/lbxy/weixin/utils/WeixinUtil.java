package com.lbxy.weixin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lbxy.core.utils.NetWorkUtil;
import com.lbxy.weixin.cache.AccessTokenCache;
import com.lbxy.weixin.dto.AccessToken;
import com.lbxy.weixin.dto.templateMessage.TemplateData;
import com.lbxy.weixin.dto.templateMessage.TemplateModel;
import com.lbxy.weixin.exception.WeixinLoginException;
import com.lbxy.weixin.properties.Api;
import com.lbxy.weixin.properties.AuthKey;
import com.lbxy.weixin.properties.Properties;

/**
 * @author lmy
 * @description WeixinUtil
 * @date 2018/8/14
 */
public class WeixinUtil {
    public static JSONObject login(String code) throws WeixinLoginException {
        String reqUrl = String.format(Api.LOGIN, AuthKey.APP_ID, AuthKey.PAY_APP_KEY, code);
        JSONObject result = NetWorkUtil.doGetUri(reqUrl);
        if (result.containsKey("errcode")) {
            throw new WeixinLoginException(result.getString("errcode"));
        }

        return result;
    }

    public static boolean sendTemplateMessage(TemplateModel model) {
        String uri = String.format(Api.SEND_TEMPLATE_MESSAGE, AccessTokenCache.getAccessToken());
        JSONObject object = NetWorkUtil.doPostUriReturnJson(uri, JSON.toJSONString(model));
        int errcode = object.getIntValue("errcode");
        return errcode == 0;
    }

    /**
     * 向指定用户发送消息
     *
     * @param toUser 被发送用户的openid
     * @param url    点击之后跳转的页面 例如：index？type=1
     * @return
     */
    public static boolean sendMessage(String toUser, String formId, String url, String projectName, String content, String replyer, String replyContent, String replyTime) {
        TemplateData templateData = new TemplateData(projectName, content, replyer, replyContent, replyTime);
        TemplateModel model = new TemplateModel(toUser, Properties.TEMPLATE_ID, formId, url, templateData);
        return sendTemplateMessage(model);
    }

    public static AccessToken getAccessTokenByRequest() {
        String uri = String.format(Api.ACCESS_TOKEN_URI, AuthKey.APP_ID, AuthKey.APP_SECRET);
        JSONObject object = NetWorkUtil.doGetUri(uri);
        return JSON.parseObject(object.toJSONString(), AccessToken.class);
    }

}
