package com.lbxy.dao;

import com.lbxy.model.User;

/**
 * @author lmy
 * @description UserDao
 * @date 2018/8/14
 */
public class UserDao {


    public int insert(User user) {
        user.save();
        return this.findByOpenid(user.getStr("openId")).getInt("id");
    }

    public User findByOpenid(String openid) {
        return User.dao.findFirst("select * from User where openid = ?", openid);
    }

    public User findById(int id) {
        return User.dao.findById(id);
    }

    public boolean update(User user) {
        return user.update();
    }

    public boolean userSave(User user){
        return user.update();
    }

}
