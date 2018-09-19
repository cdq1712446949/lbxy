package com.lbxy.core.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.lbxy.core.utils.LoggerUtil;

import java.util.Arrays;
import java.util.Map;

/**
 * @author lmy
 * @description RequestInterceptor
 * @date 2018/9/19
 */
public class RequestInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Map<String, String[]> paramMap = inv.getController().getParaMap();
        StringBuilder builder = new StringBuilder();
        paramMap.keySet().forEach(key -> builder.append("[ ").append(key).append(":").append(Arrays.toString(paramMap.get(key))).append(" ]").append("\n"));
        LoggerUtil.info(getClass(), inv.getActionKey() + ": \n" + builder);

        inv.invoke();
    }
}
