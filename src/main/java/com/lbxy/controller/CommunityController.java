package com.lbxy.controller;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.response.MessageVo;
import com.lbxy.common.response.ResponseStatus;
import com.lbxy.model.Community;
import com.lbxy.service.CommunityService;
import com.lbxy.service.impl.CommunityServiceImpl;

import javax.annotation.Resource;
import java.util.Date;

public class CommunityController extends BaseController {

    @Resource
    private CommunityService communityService;

    public void index() {
        Page<Community> list = new Page<Community>();
        try {
            list = communityService.getCommunity(getJsonParamToInt("pn"));
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("获取成功").setData(list));
        }catch (Exception e){
            renderJson(new MessageVo().setStatus(ResponseStatus.error).setMessage("未传递参数"));
        }
    }

    public void save() {

        Community community = new Community();
        getJsonParamToModel(community);
        community.set("postDate", new Date());
        boolean i = community.save();
        if (i) {
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("成功"));
        } else {
            renderJson(new MessageVo().setStatus(ResponseStatus.error).setMessage("失败"));
        }
    }

    public void delete() {

        if (communityService.deleteById(getJsonParamToInt("id"))) {
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("成功"));
        } else {
            renderJson(new MessageVo().setStatus(ResponseStatus.error).setMessage("失败"));
        }
    }

}
