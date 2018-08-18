package com.lbxy.core.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lbxy.common.InterceptorConst;
import com.lbxy.common.response.MessageVoUtil;

import java.lang.reflect.Parameter;

/**
 * @author lmy
 * @description GlobalParamInterceptor 全局controller参数拦截器，如果需要的参数没有传递过来，直接报错
 * 该拦截器使用java8的方式获取方法的参数， 编译的时候需要添加 -parameters 参数
 * @date 2018/8/14
 */
public class GlobalParamInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        boolean flag = false;
        for (Parameter parameter : inv.getMethod().getParameters()) {
            String paramName = parameter.getName();

            if (InterceptorConst.USER_ID.equals(paramName)) {
                //如果拦截到userId参数，跳过
                continue;
            }

            if (!controller.isParaExists(paramName)) {
                flag = true;
                break;
            }
        }

        if (flag) {
            //如果请求没有传递全部的参数，不再继续执行
            controller.renderJson(MessageVoUtil.error("请传递必要参数！"));
        } else {
            //如果传递了全部参数, 放行
            inv.invoke();
        }
    }
}
