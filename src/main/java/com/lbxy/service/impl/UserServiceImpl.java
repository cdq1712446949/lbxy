package com.lbxy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.exception.InvalidRequestParamException;
import com.lbxy.common.request.SaveUserInfoBean;
import com.lbxy.common.request.UpdateUserInfoBean;
import com.lbxy.common.request.VerificationBean;
import com.lbxy.common.status.UserStatus;
import com.lbxy.core.utils.JWTUtil;
import com.lbxy.dao.UserDao;
import com.lbxy.event.UpdateAcceptOrderEvent;
import com.lbxy.model.User;
import com.lbxy.service.UserService;
import com.lbxy.weixin.utils.WeixinUtil;
import net.dreamlu.event.EventKit;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Inject
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(long id) {
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
    public User updateBaseUserInfo(UpdateUserInfoBean userInfo, long userId) throws InvalidRequestParamException {
        Map<String, String> param = userInfo.getUpdateUserInfo();
        Map.Entry<String, String> paramEntry = param.entrySet().iterator().next();
        User user = userDao.findById(userId);
        user.set(paramEntry.getKey(), paramEntry.getValue());
        userDao.update(user);
        return user;
    }

    @Override
    @Before(Tx.class)
    public User updateVerificationUserInfo(VerificationBean verification, long userId) {
        User currentUser = this.findById(userId);
        currentUser.setRealName(verification.getRealName());
        currentUser.setStudentNumber(verification.getStudentNumber());
        currentUser.setStuNoPic(verification.getStuNoPic());
        currentUser.setStatus(UserStatus.WAIT_AUTHENTICATION);
        userDao.update(currentUser);
        return currentUser;
    }

    @Override
    @Before(Tx.class)
    public User saveUserInfo(SaveUserInfoBean userInfo, long userId) {
        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setUsername(userInfo.getNickName());
        currentUser.setAvatarUrl(userInfo.getAvatarUrl());
        currentUser.setGender(userInfo.getGender());

        userDao.update(currentUser);
        return currentUser;
    }

    @Override
    public void updateUserBalance(long userId, BigDecimal reward) {
        User user = this.findById(userId);
        user.setBalance(user.getBalance().add(reward));
        userDao.update(user);
    }

    @Override
    @Before(Tx.class)
    public boolean updateUserStatus(long id, int status) {
        User user = new User();
        user.set("id", id);
        user.set("status", status);
        return userDao.save(user);
    }

    @Override
    @Before(Tx.class)
    public boolean changeMoney(User user) {
        return userDao.update(user);
    }

    @Before(Tx.class)
    public int accept(long orderId, long userId) {
        User user = userDao.findById(userId);
        if (user == null) {
            return ERROR_USERID;
        }
        if (user.getPhoneNumber() == null || user.getRealName() == null) {
            return NEED_MORE_INFO;
        }

        try {
            EventKit.post(new UpdateAcceptOrderEvent(getClass(), user, orderId));
        } catch (Exception e) {
            return CANT_ACCEPT_OWN_ORDER;
        }

        return SUCCESS;
    }
}
