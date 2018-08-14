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

    public User findByOpenid(String openid) {
        return User.dao.findFirst("select * from User where openid = ?", openid);
    }

    public boolean update(User user) {
        return user.update();
    }
}
