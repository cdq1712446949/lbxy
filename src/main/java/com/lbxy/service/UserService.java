package com.lbxy.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.User;

public class UserService {
    public User findById(int id) {
        return User.dao.findById(id);
    }

    public Page<User> getAllUsers(int pn) {
        int totalNum = Db.queryInt("select count(*) from User");
        int totalPage = totalNum / 10;
        if (totalNum % 10 >= 1) {
            totalPage += 1;
        }
        if (pn >= totalPage) {
            pn = totalPage;
        }

        if (pn <= 1) {
            pn = 1;
        }
        return User.dao.paginate(pn, 10, "select *", " from User");
    }

    public Page<User> findByPhone(String phoneNumber) {
        Page<User> list = User.dao.paginate(1, 1, "select * ", "from User where phoneNumber=?", phoneNumber);

        return list;
    }
}
