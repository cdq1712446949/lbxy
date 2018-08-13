package com.lbxy.controller;

import com.lbxy.service.UserService;
import com.lbxy.service.impl.UserServiceImpl;

public class UserController extends BaseController {

    private UserService userServiceImpl;

    public UserController() {
        userServiceImpl = new UserServiceImpl();
    }


}
