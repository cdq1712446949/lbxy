package com.lbxy.controller;

import com.lbxy.model.User;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.UserServiceImpl;

public class ImageController extends BaseController {

    private UserService userService;

    public ImageController() {
        userService = new UserServiceImpl();
    }

    public void index(int id) {
        User user=userService.findById(id);
        String url=user.get("idNoPic");
        setAttr("url",url);
        render("/back/image_show.html");
    }

    public void stuNoPice(int id){
        User user=userService.findById(id);
        String url=user.get("stuNoPic");
        setAttr("url",url);
        render("/back/image_show.html");
    }

    public void throughAuthencation(int id,int status){
        userService.throughAuthencation(id,status);
    }

}
