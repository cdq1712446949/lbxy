package com.lbxy.common.exception;

import com.jfinal.core.Controller;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.exception.ExceptionHandler;

import java.util.Objects;

/**
 * @author lmy
 * @description BaseException
 * @date 2018/8/18
 */
public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 6023927979510535223L;
    private ExceptionHandler handler;
    private String message;

    BaseException(String message) {
        super(message);
        this.message = message;
    }

    BaseException(String message, ExceptionHandler handler) {
        super(message);
        this.message = message;
        this.handler = handler;
    }

    public void setHandler(ExceptionHandler handler) {
        this.handler = handler;
    }

    public void handle(Controller invController) {
        if (Objects.isNull(this.handler)) {
            this.handler = controller -> controller.renderJson(MessageVoUtil.error(message));
        }
        this.handler.handle(invController);
    }
}
