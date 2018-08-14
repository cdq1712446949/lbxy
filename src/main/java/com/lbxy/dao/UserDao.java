package com.lbxy.dao;

import com.lbxy.model.User;

/**
 * @author lmy
 * @description UserDao
 * @date 2018/8/14
 */
public class UserDao {
    public boolean insert(User user) {
        return user.save();
    }
}
