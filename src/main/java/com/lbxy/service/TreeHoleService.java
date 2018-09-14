package com.lbxy.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.model.Treehole;

import java.util.Optional;

public interface TreeHoleService {

    Page<Treehole> getAllTreeHole(int pn);

    boolean deleteTreeHole(int id);

    long save(String content, long userId);

    public JSONObject getMainById(long id);

    JSONObject getMainByPage(int pn);

    boolean reply(long userId, Optional<String> formId, ReplyBean replyBean);

    Page<Treehole> getTreeHoleByContent(int pn, String content);

}
