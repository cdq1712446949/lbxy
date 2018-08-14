package com.lbxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.interceptors.CheckLoginInterceptor;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.UserServiceImpl;

public class UserController extends BaseController {

    private UserService userService;

    public UserController() {
        userService = new UserServiceImpl();
    }


    public void login(String code) {
        JSONObject result = userService.login(code);
        renderJson(MessageVoUtil.success(result));
    }

    @Before(CheckLoginInterceptor.class)
    public void updateUserInfo(String userInfo, int userId) {
        JSONObject jsonObject = JSON.parseObject(userInfo);
        userService.updateUserInfo(jsonObject, userId);
        renderJson(userId);
    }
}
