package com.lbxy.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.response.MessageVo;
import com.lbxy.common.response.ResponseStatus;
import com.lbxy.model.Community;
import com.lbxy.service.CommunityService;
import org.apache.log4j.or.jms.MessageRenderer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommunityController extends BaseController {

    public CommunityService communityService;

    public CommunityController() {
        communityService = new CommunityService();
    }

    public void index() {
        Page<Community> list = new Page<Community>();
        try {
            list = communityService.getCommunity(getJsonParamToInt("pn"));
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("获取成功").setData(list));
        }catch (Exception e){
            renderJson(new MessageVo().setStatus(ResponseStatus.error).setMessage("未传递参数"));
        }
//        if (getJsonParamToInt("pn") < 1) {
//
//        }else {
//            renderJson(new MessageVo().setStatus(ResponseStatus.error).setMessage("获取帖子失败"));
//        }
    }

    public void save() {

        Community community = new Community();
        getJsonParam(community);
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
