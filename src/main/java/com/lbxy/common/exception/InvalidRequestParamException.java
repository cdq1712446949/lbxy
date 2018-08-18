package com.lbxy.common.exception;

import com.lbxy.core.interceptors.exception.ExceptionHandler;

/**
 * @author lmy
 * @description InvalidRequestParamException
 * @date 2018/8/17
 */
public class InvalidRequestParamException extends BaseException {

    private static final long serialVersionUID = -5901939442721311868L;

    public InvalidRequestParamException(String msg) {
        super(msg);
    }

    public InvalidRequestParamException(String msg, ExceptionHandler handler) {
        super(msg, handler);
    }
}
