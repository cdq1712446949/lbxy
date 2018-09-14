package com.lbxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.ext.interceptor.POST;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.service.FormService;

import javax.annotation.Resource;

/**
 * @author lmy
 * @description FormController
 * @date 2018/9/11
 */
@Before(WeixinLoginInterceptor.class)
public class FormController extends Controller {

    @Resource
    private FormService formService;

    @Before(POST.class)
    public void post(long userId, String formIdsJsonString) {
        JSONArray formIds = JSON.parseArray(formIdsJsonString);
        formService.put(userId, formIds);
        renderJson(MessageVoUtil.success("formId保存成功"));
    }


}
