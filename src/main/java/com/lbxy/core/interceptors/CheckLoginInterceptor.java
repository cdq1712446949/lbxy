package com.lbxy.core.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.lbxy.common.InterceptorConst;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.utils.JWTUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author lmy
 * @description CheckLoginInterceptor 检查微信小程序的用户是否登陆，如果登陆将被调用方法的第一个参数设置为从token中解析出来的userid
 * 被此拦截器拦截的方法的第一个参数一定要为user id
 * @date 2018/8/14
 */
public class CheckLoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        String token = inv.getController().getHeader("token");
        if (StringUtils.isBlank(token)) {
            inv.getController().renderJson(MessageVoUtil.needLogin());
        } else {
            int userId = JWTUtil.verifyToken(token);
            if (userId == -1) {
                inv.getController().renderJson(MessageVoUtil.needLogin());
            } else {

                if (this.containsParameter(inv.getMethod(), InterceptorConst.USER_ID)) {
                    inv.setArg(getParameterIndex(inv.getMethod(), InterceptorConst.USER_ID), userId);
                }

                inv.invoke();
            }
        }
    }

    private boolean containsParameter(Method method, String parameterName) {
        boolean flag = false;
        for (Parameter parameter : method.getParameters()) {

            if (parameterName.equals(parameter.getName())) {
                flag = true;
            }
        }
        return flag;
    }

    private int getParameterIndex(Method method, String parameterName) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameterName.equals(parameters[i].getName())) {
                return i;
            }
        }
        return 0;
    }
}
