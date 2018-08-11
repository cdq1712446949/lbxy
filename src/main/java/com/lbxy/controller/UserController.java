package com.lbxy.controller;

import com.jfinal.core.Controller;
import com.lbxy.service.UserService;

public class UserController extends BaseController {

    private UserService userService;

    public UserController() {
        userService = new UserService();
    }



}
