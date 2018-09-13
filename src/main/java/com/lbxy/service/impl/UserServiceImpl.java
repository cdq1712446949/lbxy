package com.lbxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.exception.InvalidRequestParamException;
import com.lbxy.common.request.UserInfoBean;
import com.lbxy.common.request.VerificationBean;
import com.lbxy.common.status.UserStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.core.utils.JWTUtil;
import com.lbxy.dao.UserDao;
import com.lbxy.model.User;
import com.lbxy.service.UserService;
import com.lbxy.weixin.utils.WeixinUtil;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    public User findById(int id) {
        return userDao.findById(id);
    }

    public Page<User> getAllUsers(int pn) {
        int totalNum = userDao.getTotalNumber();
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
        return userDao.findUsersByPn(pn);
    }

    public Page<User> findByPhone(String phoneNumber) {
        return userDao.findByPhone(phoneNumber);
    }

    @Override
    @Before(Tx.class)
    public JSONObject login(String code) {
        JSONObject result = WeixinUtil.login(code);
        User user = userDao.findByOpenid(result.getString("openid"));
        JSONObject returnValue = new JSONObject();
        long userId = -1;
        if (user != null) {
            //更新sessionKey
            user.set("sessionKey", result.getString("session_key"));
            userDao.update(user);

            userId = user.getInt("id");
            returnValue.put("isNew", false);
        } else {
            user = new User();
            user.set("openId", result.getString("openid"));
            user.set("sessionKey", result.getString("session_key"));
            userDao.insert(user);
            userId = user.getId();

            returnValue.put("isNew", true);
        }

        returnValue.put("token", JWTUtil.createToken(userId));
        returnValue.put("user", user);
        return returnValue;
    }

    @Override
    @Before(Tx.class)
    public User updateBaseUserInfo(UserInfoBean userInfo, int userId) throws InvalidRequestParamException {
        Map<String, String> param = userInfo.getUpdateUserInfo();
        Map.Entry<String, String> paramEntry = param.entrySet().iterator().next();
        User user = userDao.findById(userId);
        user.set(paramEntry.getKey(), paramEntry.getValue());
        user.update();
        return user;
    }

    @Override
    @Before(Tx.class)
    public User updateVerificationUserInfo(VerificationBean verification, int userId) {
        User currentUser = this.findById(userId);
        currentUser.setReadName(verification.getRealName());
        currentUser.setStudentNumber(verification.getStudentNumber());
        currentUser.setStuNoPic(verification.getStuNoPic());
        currentUser.setStatus(UserStatus.WAIT_AUTHENTICATION);
        currentUser.update();
        return currentUser;
    }

    @Override
    @Before(Tx.class)
    public User saveUserInfo(JSONObject userInfo, int userId) {
        User currentUser = userDao.findById(userId);
        currentUser.set("username", userInfo.get("nickName"));
        currentUser.set("avatarUrl", userInfo.get("avatarUrl"));
        currentUser.set("gender", userInfo.get("gender"));
        currentUser.update();
        return currentUser;
    }

    @Override
    @Before(Tx.class)
    public boolean updateUserStatus(long id, int status) {
        User user = new User();
        user.set("id", id);
        user.set("status", status);
        return userDao.userSave(user);
    }

    @Override
    public BigDecimal getUserAccountBalance(long userId) {
        return userDao.getUserBalance(userId);
    }

    @Override
    @Before(Tx.class)
    public boolean changeMoney(User user) {
        return userDao.update(user);
    }

}
