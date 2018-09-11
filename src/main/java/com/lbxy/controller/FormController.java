package com.lbxy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
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

    public void post(long userId, String formIdsJsonString) {
        JSONObject formIds = JSON.parseObject(formIdsJsonString);
        formService.put(userId, formIds);
        renderJson(MessageVoUtil.success("formId保存成功"));
    }


}
