package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.CheckLoginInterceptor;
import com.lbxy.service.NotificationService;

import javax.annotation.Resource;

/**
 * @author lmy
 * @description NotificationController
 * @date 2018/8/28
 */
@Before(CheckLoginInterceptor.class)
public class NotificationController extends Controller {

    @Resource
    private NotificationService notificationService;

    public void index() {
        renderJson(MessageVoUtil.success("获取成功", notificationService.getActiveNotification()));
    }
}
