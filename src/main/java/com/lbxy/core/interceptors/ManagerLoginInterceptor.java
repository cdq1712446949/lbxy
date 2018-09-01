package com.lbxy.core.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

/**
 * @author lmy
 * @description ManagerLoginInterceptor
 * @date 2018/8/28
 */
public class ManagerLoginInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller target = inv.getController();

        String username = target.getSessionAttr("userName.login");
        System.out.println("username:"+username);
        if (StrKit.isBlank(username)) {
//            inv.invoke();
            //跳转到登陆界面
            System.out.println("返回登陆界面");
            target.render("login.html");
        } else {
            //放行
            System.out.println("放行");
            inv.invoke();
        }
    }
}
