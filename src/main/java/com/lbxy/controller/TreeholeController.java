package com.lbxy.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.lbxy.common.request.PostBean;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.service.TreeHoleService;

import javax.annotation.Resource;

/**
 * @author lmy
 * @description TreeholeController
 * @date 2018/8/26
 */
public class TreeholeController extends Controller {
    @Resource
    private TreeHoleService treeHoleService;

    public void post(@ValidParam @Para("") PostBean postBean, long userId) {
        long treeholeId = treeHoleService.save(postBean.getContent(), userId);
        renderJson(MessageVoUtil.success("发布成功", treeholeId));
    }

    public void index(int pn) {
        renderJson(MessageVoUtil.success("请求成功", treeHoleService.getMainByPage(pn)));
    }

    public void reply(@ValidParam @Para("") ReplyBean replyBean, long userId) {
        boolean result = treeHoleService.reply(userId,replyBean.getpId(), replyBean.getpUserId(), replyBean.getContent());
        if (result) {
            renderJson(MessageVoUtil.success("回复成功"));
        } else {
            renderJson(MessageVoUtil.error("回复失败"));
        }
    }
}