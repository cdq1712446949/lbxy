package com.lbxy.common;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

public class LoginInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        Controller ctl = invocation.getController();
        //判断用户是否登录
        String sid = ctl.getSession().getId();
        Record user = CacheKit.get("LoginUserCache", sid);
        String action = invocation.getActionKey();
        if (user != null || action.indexOf("login") >= 0) {
            invocation.invoke();
        } else {
            ctl.redirect("/login");
        }
    }
}