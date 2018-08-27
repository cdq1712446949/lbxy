package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.lbxy.common.request.PostBean;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.core.interceptors.CheckLoginInterceptor;
import com.lbxy.service.LostFoundService;

import javax.annotation.Resource;

/**
 * @author lmy
 * @description LostfoundController
 * @date 2018/8/26
 */
@Before(CheckLoginInterceptor.class)
public class LostfoundController extends Controller {
    @Resource
    private LostFoundService lostFoundService;

    public void post(@ValidParam @Para("") PostBean postBean, long userId) {
        long lostFoundId = lostFoundService.save(postBean.getContent(), userId);
        renderJson(MessageVoUtil.success("发布成功", lostFoundId));
    }

    public void index(int pn) {
        renderJson(MessageVoUtil.success("请求成功", lostFoundService.getMainByPage(pn)));
    }

    public void reply(@ValidParam @Para("") ReplyBean replyBean, long userId) {
        boolean result = lostFoundService.reply(userId,replyBean.getpId(), replyBean.getpUserId(), replyBean.getContent());
        if (result) {
            renderJson(MessageVoUtil.success("回复成功"));
        } else {
            renderJson(MessageVoUtil.error("回复失败"));
        }
    }
}
