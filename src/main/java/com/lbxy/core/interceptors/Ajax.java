package com.lbxy.core.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.lbxy.common.response.MessageVoUtil;

/**
 * @author lmy
 * @description Ajax
 * @date 2018/8/18
 */
public class Ajax implements Interceptor {
    @Override
    public void intercept(Invocation inv) {

        String header = inv.getController().getHeader("X-Requested-With");
        if ("XMLHttpRequest".equalsIgnoreCase(header)) {
            inv.getController().renderJson(MessageVoUtil.error("非ajax请求"));
        } else {
            inv.invoke();
        }
    }
}
