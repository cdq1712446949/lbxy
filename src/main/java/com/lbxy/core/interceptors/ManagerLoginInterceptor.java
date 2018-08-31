package com.lbxy.core.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.lbxy.common.CacheNameConst;
import com.lbxy.common.InterceptorConst;

import static com.lbxy.core.utils.InterceptorUtil.containsParameter;
import static com.lbxy.core.utils.InterceptorUtil.getParameterIndex;

/**
 * @author lmy
 * @description ManagerLoginInterceptor
 * @date 2018/8/28
 */
public class ManagerLoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller target = inv.getController();

        String username = CacheKit.get(CacheNameConst.MANAGER_LOGIN_CACHE, "username.login");
        if (StrKit.isBlank(username)) {
            //跳转到登陆界面
            target.render("login.html");
        } else {
            //放行
            String paramName = InterceptorConst.MANAGER_USERNAME;
            if (containsParameter(inv.getMethod(), paramName)) {
                inv.setArg(getParameterIndex(inv.getMethod(), paramName), username);
            }
            inv.invoke();
        }
    }
}
