package com.lbxy.core.interceptors.exception;

import com.jfinal.core.Controller;

/**
 * @author lmy
 * @description ExceptionHandler
 * @date 2018/8/18
 */
@FunctionalInterface
public interface ExceptionHandler {
    void handle(Controller invController);
}
