package com.lbxy.core.interceptors;

import com.jfinal.aop.Invocation;

public class RoleInterceptor implements com.jfinal.aop.Interceptor {
    @Override
    public void intercept(Invocation invocation) {
        System.out.println("放行之前");
        invocation.invoke();
        System.out.println("放行之后");
    }
}
