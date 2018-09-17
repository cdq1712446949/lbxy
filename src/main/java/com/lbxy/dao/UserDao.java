package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.User;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description UserDao
 * @date 2018/8/14
 */
@Repository
public class UserDao {

    public boolean insert(User user) {
        return user.save();
    }

    public User findByOpenid(String openid) {
        return User.DAO.findFirst("select * from user where openid = ?", openid);
    }

    public User findById(long id) {
        return User.DAO.findById(id);
    }

    public Page<User> findUsersByPn(int pn){
        return User.DAO.paginate(pn,10,"select *", " from user");
    }

    public Page<User> findByPhone(String phoneNumber){
        return User.DAO.paginate(1, 1, "select * ", "from user where phoneNumber=?", phoneNumber);
    }

    public boolean update(User user) {
        return user.update();
    }

    public boolean save(User user) {
        return user.update();
    }

    public int getTotalNumber() {
        return Db.queryInt("select count(*) from user");
    }
}
