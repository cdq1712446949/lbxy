package com.lbxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.paragetter.Para;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.lbxy.common.request.SaveUserInfoBean;
import com.lbxy.common.request.UpdateUserInfoBean;
import com.lbxy.common.request.VerificationBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.core.plugins.cache.Injector;
import com.lbxy.manager.UploadManager;
import com.lbxy.model.Bill;
import com.lbxy.model.User;
import com.lbxy.service.BillService;
import com.lbxy.service.OrderService;
import com.lbxy.service.UserService;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.math.BigDecimal;
import java.util.Optional;

@Before(WeixinLoginInterceptor.class)
public class UserController extends BaseController {

    @Inject
    private UserService userService;
    @Inject
    private OrderService orderService;
    @Inject
    private BillService billService;
    @Inject
    private UploadManager uploadManager;

    public UserController() {
        Injector.getInjector().injectMembers(this);
    }

    @Clear(WeixinLoginInterceptor.class)
    @Before(POST.class)
    public void login(@NotBlank String code) {
        JSONObject result = userService.login(code);
        renderJson(MessageVoUtil.success(result));
    }

    @Before(POST.class)
    public void updateUserInfo(UpdateUserInfoBean userInfo, long userId) {
        User result = userService.updateBaseUserInfo(userInfo, userId);
        renderJson(MessageVoUtil.success("更新成功", result));
    }

    @Before(POST.class)
    public void saveUserInfo(@ValidParam @Para("") SaveUserInfoBean userInfo, long userId) {
        User result = userService.saveUserInfo(userInfo, userId);
        renderJson(MessageVoUtil.success("保存用户信息成功", result));
    }

    @Before(POST.class)
    public void auth(UploadFile img, long userId, @ValidParam @Para("") VerificationBean verification) {
        String imagePath = img.getUploadPath() + File.separatorChar + img.getFileName();
        Optional<String> imageUrl = uploadManager.upload2SM(imagePath);

        if (imageUrl.isPresent()) {
            verification.setStuNoPic(imageUrl.get());
        } else {
            String url = PropKit.get("server.host") + File.separatorChar + "upload" + File.separatorChar + img.getFileName();
            verification.setStuNoPic(url);
        }

        User result = userService.updateVerificationUserInfo(verification, userId);
        renderJson(MessageVoUtil.success("更新用户信息成功", result));
    }

    @Before(GET.class)
    public void account(long userId) {
        BigDecimal balance = userService.findById(userId).getBalance();
        BigDecimal waitSettle = orderService.getWaitSettledReward(userId);
        BigDecimal weeklyIncome = billService.get7DaysTotalIncome(userId);
        JSONObject result = new JSONObject();
        result.put("balance", balance);
        result.put("waitSettle", waitSettle);
        result.put("weeklyIncome", weeklyIncome);
        renderJson(MessageVoUtil.success("请求成功", result));
    }

    @Before(GET.class)
    public void accountDetail(int pn, long userId) {
        Page<Bill> bills = billService.getAllByUserId(pn, userId);
        renderJson(MessageVoUtil.success("请求成功", bills));
    }

    @Before(GET.class)
    public void getUserInfo(long userId) {
        renderJson(MessageVoUtil.success(userService.findById(userId)));
    }

}
