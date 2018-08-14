package com.lbxy.service;

import com.alibaba.fastjson.JSONObject;
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

    JSONObject login(String code);
}
