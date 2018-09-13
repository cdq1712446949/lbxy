package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.service.NotificationService;

import javax.annotation.Resource;

/**
 * @author lmy
 * @description NotificationController
 * @date 2018/8/28
 */
@Before(WeixinLoginInterceptor.class)
public class NotificationController extends Controller {

    @Resource
    private NotificationService notificationService;

    @Before({GET.class, CacheInterceptor.class})
    @CacheName("notification")
    public void index() {
        renderJson(MessageVoUtil.success("获取成功", notificationService.getActiveNotification()));
    }
}
