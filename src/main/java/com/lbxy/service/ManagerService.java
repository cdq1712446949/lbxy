package com.lbxy.service;

import com.lbxy.model.Manager;

/**
 * @author lmy
 * @description ManagerServiceImpl
 * @date 2018/8/14
 */
public interface ManagerService {
    byte NOT_EXIST = 0;
    byte INVALID_PASSWORD = 1;
    byte SUCCESS = 2;

    int login(String username, String password);

    Manager getManager(String username);

}
