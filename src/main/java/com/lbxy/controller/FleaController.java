package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.lbxy.common.request.PostBean;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.service.FleaService;
import com.lbxy.service.FormService;

import javax.annotation.Resource;

/**
 * @author lmy
 * @description FleaController
 * @date 2018/8/26
 */
@Before(WeixinLoginInterceptor.class)
public class FleaController extends Controller {

    @Resource
    private FleaService fleaService;

    @Resource
    private FormService formService;

    @Before(POST.class)
    public void post(@ValidParam @Para("") PostBean postBean, long userId) {
        long fleaId = fleaService.save(postBean.getContent(), userId);
        renderJson(MessageVoUtil.success("发布成功", fleaId));
    }


    @Before(GET.class)
    public void index(int pn) {
        renderJson(MessageVoUtil.success("请求成功", fleaService.getMainByPage(pn)));
    }

    @Before(POST.class)
    public void reply(@ValidParam @Para("") ReplyBean replyBean, long userId) throws Exception {
        String formId = formService.get(userId);
        boolean result = fleaService.reply(userId, formId, replyBean);
        if (result) {
            renderJson(MessageVoUtil.success("回复成功"));
        } else {
            renderJson(MessageVoUtil.error("回复失败"));
        }
    }

    @Before(GET.class)
    public void id(long id) {
        renderJson(MessageVoUtil.success("请求成功", fleaService.getMainById(id)));
    }
}
