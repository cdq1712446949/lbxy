package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.User;

/**
 * @author lmy
 * @description ManagerServiceImpl
 * @date 2018/8/14
 */
public interface ManagerService {
    int login(String username, String password);

    Page<User> getUser(int pn);
}
