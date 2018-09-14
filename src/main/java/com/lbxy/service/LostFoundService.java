package com.lbxy.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.model.Lostfound;

import java.util.Optional;

public interface LostFoundService {

    Page<Lostfound> getAllLostFound(int pn);

    boolean deleteLostFound(int id);

    long save(String content, long userId);

    public JSONObject getMainById(long id);

    JSONObject getMainByPage(int pn);

    boolean reply(long userId, Optional<String> formId, ReplyBean replyBean);

    Page<Lostfound> getLostFoundByContent(int pn, String content);

}
