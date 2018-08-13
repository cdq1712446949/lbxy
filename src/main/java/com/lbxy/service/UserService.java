package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.User;

/**
 * @author lmy
 * @description UserServiceImpl
 * @date 2018/8/14
 */
public interface UserService {
    User findById(int id);

    Page<User> getAllUsers(int pn);

    Page<User> findByPhone(String phoneNumber);
}
