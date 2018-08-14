package com.lbxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.model.User;
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

    public void updateUserInfo(String userInfo) {
        //TODO
    }
}
