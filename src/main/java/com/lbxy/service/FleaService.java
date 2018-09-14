package com.lbxy.service;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.model.Flea;

import java.util.Optional;

public interface FleaService {

    Page<Flea> getAllFlea(int pn);

    boolean deleteFlea(int id);

    long save(String content, long userId);

    JSONObject getMainById(long id);

    JSONObject getMainByPage(int pn);

    boolean reply(long userId, Optional<String> formId, ReplyBean replyBean);

    Page<Flea> getFleaByContent(int pn, String content);

}
