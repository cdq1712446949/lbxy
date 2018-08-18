package com.lbxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.POST;
import com.lbxy.common.request.UserInfoBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.CheckLoginInterceptor;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.UserServiceImpl;

public class UserController extends BaseController {

    private UserService userService;

    public UserController() {
        userService = new UserServiceImpl();
    }


    @Before(POST.class)
    public void login(String code) {
        JSONObject result = userService.login(code);
        renderJson(MessageVoUtil.success(result));
    }

    @Before({CheckLoginInterceptor.class, POST.class})
    public void updateUserInfo(UserInfoBean userInfo, int userId) {
        boolean result;
        result = userService.updateUserInfo(userInfo, userId);

        if (result) {
            renderJson(MessageVoUtil.success("更新成功"));
        } else {
            renderJson(MessageVoUtil.success("更新失败"));
        }
    }

}
