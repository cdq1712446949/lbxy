package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.lbxy.common.request.PostBean;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.core.plugins.cache.Injector;
import com.lbxy.service.FormService;
import com.lbxy.service.TreeHoleService;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Optional;

/**
 * @author lmy
 * @description TreeholeController
 * @date 2018/8/26
 */
@Before(WeixinLoginInterceptor.class)
public class TreeholeController extends Controller {
    @Inject
    private TreeHoleService treeHoleService;
    @Inject
    private FormService formService;


    public TreeholeController() {
        Injector.getInjector().injectMembers(this);
    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("treehole")
    public void post(@ValidParam @Para("") PostBean postBean, long userId) {
        long treeholeId = treeHoleService.save(postBean.getContent(), userId);
        renderJson(MessageVoUtil.success("发布成功", treeholeId));
    }

    @Before({GET.class, CacheInterceptor.class})
    @CacheName("treehole")
    public void index(int pn) {
        renderJson(MessageVoUtil.success("请求成功", treeHoleService.getMainByPage(pn)));
    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("treehole")
    public void reply(@ValidParam @Para("") ReplyBean replyBean, long userId) throws Exception {
        Optional<String> formId = formService.get(userId);
        boolean result = treeHoleService.reply(userId, formId, replyBean);
        if (result) {
            renderJson(MessageVoUtil.success("回复成功"));
        } else {
            renderJson(MessageVoUtil.error("回复失败"));
        }
    }

    @Before(GET.class)
    public void id(long id) {
        renderJson(MessageVoUtil.success("请求成功", treeHoleService.getMainById(id)));
    }
}
