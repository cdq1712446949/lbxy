package com.lbxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.ext.interceptor.POST;
import com.lbxy.common.request.UserInfoBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.CheckLoginInterceptor;
import com.lbxy.model.User;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.UserServiceImpl;

@Before(CheckLoginInterceptor.class)
public class UserController extends BaseController {

    private UserService userService;

    public UserController() {
        userService = new UserServiceImpl();
    }


    @Clear(CheckLoginInterceptor.class)
    @Before(POST.class)
    public void login(String code) {
        JSONObject result = userService.login(code);
        renderJson(MessageVoUtil.success(result));
    }

    @Before(POST.class)
    public void updateUserInfo(UserInfoBean userInfo, int userId) {
        User result = userService.updateUserInfo(userInfo, userId);
        renderJson(MessageVoUtil.success("更新成功", result));
    }

    @Before(POST.class)
    public void saveUserInfo(String userInfo,int userId) {
        JSONObject user = JSON.parseObject(userInfo);
        User result = userService.saveUserInfo(user, userId);
        renderJson(MessageVoUtil.success("保存用户信息成功", result));
    }

}
