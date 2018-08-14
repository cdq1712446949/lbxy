package com.lbxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.dao.UserDao;
import com.lbxy.model.User;
import com.lbxy.service.UserService;
import com.lbxy.utils.JWTUtil;
import com.lbxy.weixin.utils.WeixinUtil;

public class UserServiceImpl implements UserService {
    public static int SUCCESS = 0;
    public static int ERROR = 1;

    private UserDao userDao;

    public UserServiceImpl() {
        userDao = new UserDao();
    }

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

    @Override
    public JSONObject login(String code) {
        JSONObject result = WeixinUtil.login(code);
        User user = userDao.findByOpenid(result.getString("openid"));
        JSONObject returnValue = null;
        if (user != null) {
            //更新sessionKey
            user.set("sessionKey", result.getString("session_key"));
            userDao.update(user);

            returnValue.put("isNew", false);
        } else {
            user = new User();
            user.set("openId", result.getString("openid"));
            user.set("sessionKey", result.getString("session_key"));
            userDao.insert(user);

            returnValue = JSON.parseObject(user.toJson());
            returnValue.put("isNew", true);
        }

        returnValue.put("token", JWTUtil.createToken());
        returnValue.put("user", user);
        return returnValue;
    }

}
