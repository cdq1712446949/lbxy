package com.lbxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.paragetter.Para;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.upload.UploadFile;
import com.lbxy.common.request.UserInfoBean;
import com.lbxy.common.request.VerificationBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.CheckLoginInterceptor;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.model.Bill;
import com.lbxy.model.User;
import com.lbxy.service.BillService;
import com.lbxy.service.OrderService;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.BillServiceImpl;
import com.lbxy.service.impl.OrderServiceImpl;
import com.lbxy.service.impl.UserServiceImpl;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@Before(CheckLoginInterceptor.class)
public class UserController extends BaseController {

    private UserService userService;

    private OrderService orderService;

    private BillService billService;

    public UserController() {
        billService = new BillServiceImpl();
        orderService = new OrderServiceImpl();
        userService = new UserServiceImpl();
    }


    @Clear(CheckLoginInterceptor.class)
    @Before(POST.class)
    public void login(String code) {
        JSONObject result = userService.login(code);
        renderJson(MessageVoUtil.success(result));
    }

    @Before(POST.class)
    public void updateUserInfo(UserInfoBean userInfo, int userId) {
        User result = userService.updateBaseUserInfo(userInfo, userId);
        renderJson(MessageVoUtil.success("更新成功", result));
    }

    @Before(POST.class)
    public void saveUserInfo(String userInfo, int userId) {
        JSONObject user = JSON.parseObject(userInfo);
        User result = userService.saveUserInfo(user, userId);
        renderJson(MessageVoUtil.success("保存用户信息成功", result));
    }

    @Before(POST.class)
    public void auth(UploadFile img, int userId, @ValidParam @Para("") VerificationBean verification) {
        //TODO 部署之后还要调试上传路径
        verification.setStuNoPic(img.getUploadPath() + File.separator + img.getFileName());
        User result = userService.updateVerificationUserInfo(verification, userId);
        renderJson(MessageVoUtil.success("更新用户信息成功", result));
    }

    @Before(GET.class)
    public void account(int userId) {
        BigDecimal balance = userService.getUserAccountBalance(userId);
        BigDecimal waitSettle = orderService.getWaitSettledReward(userId);
        BigDecimal weeklyIncome = billService.get7DaysTotalIncome(userId);
        JSONObject result = new JSONObject();
        result.put("balance", balance);
        result.put("waitSettle", waitSettle);
        result.put("weeklyIncome", weeklyIncome);
        renderJson(MessageVoUtil.success("请求成功", result));
    }

    @Before(GET.class)
    public void accountDetail(int userId) {
        List<Bill> bills = billService.getAllByUserId(userId);
        renderJson(MessageVoUtil.success("请求成功", bills));
    }
}
