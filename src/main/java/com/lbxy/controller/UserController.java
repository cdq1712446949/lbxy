package com.lbxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.lbxy.model.User;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.UserServiceImpl;
import com.lbxy.weixin.utils.WeixinUtil;

public class UserController extends BaseController {

    private UserService userService;

    public UserController() {
        userService = new UserServiceImpl();
    }


    public void login(String code) {
        int result = userService.login(code);

    }
}
