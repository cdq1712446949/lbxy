package com.lbxy.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Treehole;

public interface TreeHoleService {

    Page<Treehole> getAllTreeHole(int pn);

    boolean deleteTreeHole(int id);

    long save(String content, long userId);

    JSONObject getMainByPage(int pn);

    boolean reply(long userId, Long pId, Long pUserId,  Long toUserId, String formId, String content);
}
