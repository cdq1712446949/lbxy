package com.lbxy.weixin.exception;

/**
 * @author lmy
 * @description WeixinLoginException
 * @date 2018/8/14
 */
public class WeixinLoginException extends RuntimeException {

    private static final long serialVersionUID = -5252830253836357505L;

    public WeixinLoginException(String msg) {
        super(msg);
    }
}
