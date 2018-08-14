package com.lbxy.controller;

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
        if (code == null) {
            renderJson(MessageVoUtil.error("请输入参数！"));
        }

        User result = userService.login(code);
        renderJson(result);
    }
}
