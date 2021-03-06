package com.lbxy.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.SaveUserInfoBean;
import com.lbxy.common.request.UpdateUserInfoBean;
import com.lbxy.common.request.VerificationBean;
import com.lbxy.model.User;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description UserServiceImpl
 * @date 2018/8/14
 */
public interface UserService {
    int SUCCESS = 0;
    int ERROR_USERID = 1;
    int ORDER_NOT_EXIST = 2;
    int NEED_MORE_INFO = 3;
    int CANT_ACCEPT_OWN_ORDER = 4;

    User findById(long id);

    Page<User> getAllUsers(int pn);

    Page<User> findByPhone(String phoneNumber);

    JSONObject login(String code);

    User updateBaseUserInfo(UpdateUserInfoBean userInfo, long userId);

    User updateVerificationUserInfo(VerificationBean verification, long userId);

    User saveUserInfo(SaveUserInfoBean userInfo, long userId);

    void updateUserBalance(long userId, BigDecimal reward);

    boolean updateUserStatus(long id,int status);

    boolean changeMoney(User user);

    int accept(long orderId, long userId);

}
