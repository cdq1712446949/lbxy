package com.lbxy.core.interceptors.exception;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lbxy.common.exception.BaseException;
import com.lbxy.common.response.MessageVoUtil;

/**
 * @author lmy
 * @description ExceptionsInterceptor
 * @date 2018/8/18
 */
public class ExceptionsInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller invController = inv.getController();
        try {
            inv.invoke();
        } catch (Exception e) {
            if (e instanceof BaseException) {
                ((BaseException) e).handle(invController);
            } else {
                invController.renderJson(MessageVoUtil.error(e.getMessage()));
            }
        }
    }
}
