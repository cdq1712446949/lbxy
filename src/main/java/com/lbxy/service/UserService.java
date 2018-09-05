package com.lbxy.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.UserInfoBean;
import com.lbxy.common.request.VerificationBean;
import com.lbxy.model.User;

import java.math.BigDecimal;

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

    User updateBaseUserInfo(UserInfoBean userInfo, int userId);

    User updateVerificationUserInfo(VerificationBean verification, int userId);

    User saveUserInfo(JSONObject userInfo, int userId);

    boolean throughAuthentication(long id, int status);

    BigDecimal getUserAccountBalance(long userId);

    boolean changeMoney(User user);

}
