package com.lbxy.common.response;

public class MessageVoUtil {
    public static <T> MessageVo success(String message, T data) {
        return new MessageVo().setData(ResponseStatus.success).setMessage(message).setData(data);
    }

    public static <T> MessageVo success(T data) {
        return new MessageVo().setData(ResponseStatus.success).setMessage("处理成功").setData(data);
    }

    public static MessageVo success(String message) {
        return new MessageVo().setMessage(message);
    }

    public static MessageVo error(String msg) {
        return new MessageVo().setStatus(ResponseStatus.error).setMessage(msg);
    }

    public static MessageVo needLogin() {
        return new MessageVo().setStatus(ResponseStatus.unLogin).setMessage("未登录！");
    }
}
