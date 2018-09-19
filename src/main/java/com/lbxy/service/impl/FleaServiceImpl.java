package com.lbxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.ImageType;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.utils.LoggerUtil;
import com.lbxy.dao.FleaDao;
import com.lbxy.dao.ImageDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Flea;
import com.lbxy.model.Image;
import com.lbxy.model.User;
import com.lbxy.service.FleaService;
import com.lbxy.weixin.utils.WeixinUtil;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FleaServiceImpl implements FleaService {

    private final FleaDao fleaDao;

    private final UserDao userDao;

    private final ImageDao imageDao;

    @Inject
    public FleaServiceImpl(FleaDao fleaDao, UserDao userDao, ImageDao imageDao) {
        this.fleaDao = fleaDao;
        this.userDao = userDao;
        this.imageDao = imageDao;
    }

    @Override
    public Page<Flea> getAllFlea(int pn) {
        return fleaDao.findFleaByPn(pn);
    }

    @Override
    @Before(Tx.class)
    public boolean deleteFlea(int id) {
        Flea flea = new Flea();
        flea.set("id", id);
        flea.set("status", CommonStatus.DELETED);
        return fleaDao.update(flea);
    }

    @Override
    @Before(Tx.class)
    public long save(String content, long userId) {
        Flea flea = new Flea();
        flea.setUserId(userId);
        flea.setContent(content);
        flea.setPostDate(new Date());
        fleaDao.save(flea);
        return flea.getId();
    }

    @Override
    public JSONObject getMainByPage(int pn) {

        Page<Record> page = fleaDao.getMainByPage(pn);
        LoggerUtil.debug(getClass(), JSON.toJSONString(fleaDao.getMainByPage(pn)));

        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject one = jsonArray.getJSONObject(i);

            //将每一条回复放入结果集
            List<Record> reply = fleaDao.getReplyByPId(one.getIntValue("id"));

            one.put("reply", reply);

            // 将每一条的图片放入结果集
            List<Image> images = imageDao.getImagesByContentIdAndType(one.getIntValue("id"), ImageType.FLEA);
            one.put("images", images);
        }

        return jsonObject;
    }

    @Override
    public JSONObject getMainById(long id) {

        Flea page = fleaDao.getById(id);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));

        //将用户信息放入结果集中
        User userInMain = userDao.findById(jsonObject.getIntValue("userId"));
        jsonObject.put("username", userInMain.getUsername());
        jsonObject.put("userId", userInMain.getId());
        jsonObject.put("avatarUrl", userInMain.getAvatarUrl());

        //将每一条回复放入结果集
        List<Record> reply = fleaDao.getReplyByPId(jsonObject.getIntValue("id"));

        jsonObject.put("reply", reply);

        // 将每一条的图片放入结果集
        List<Image> images = imageDao.getImagesByContentIdAndType(jsonObject.getIntValue("id"), ImageType.FLEA);
        jsonObject.put("images", images);
        return jsonObject;
    }

    @Override
    @Before(Tx.class)
    public boolean reply(long userId, Optional<String> formId, ReplyBean replyBean) {
        User currentUser = userDao.findById(userId);
        User toUser = userDao.findById(replyBean.getToUserId());
        Flea currentFlea = fleaDao.getById(replyBean.getpId());

        if (formId.isPresent()) {
            WeixinUtil.sendMessage(toUser.getOpenId(),
                    formId.get(),
                    String.format("/pages/community/detail/detail?id=%s&type=0", replyBean.getpId()),
                    "跳蚤市场",
                    currentFlea.getContent(),
                    currentUser.getUsername(),
                    replyBean.getContent(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));

            if (Objects.nonNull(replyBean.getpUserId())) {
                User pUser = userDao.findById(replyBean.getpUserId());
                WeixinUtil.sendMessage(pUser.getOpenId(),
                        formId.get(),
                        String.format("/pages/community/detail/detail?id=%s&type=0", replyBean.getpId()),
                        "跳蚤市场",
                        currentFlea.getContent(),
                        currentUser.getUsername(),
                        replyBean.getContent(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));
            }
        } else {
            LoggerUtil.info(getClass(), userId + "无可用formid");
        }

        Flea flea = new Flea();
        flea.setUserId(userId);
        flea.setPId(replyBean.getpId());
        flea.setPUserId(replyBean.getpUserId());
        flea.setContent(replyBean.getContent());
        flea.setPostDate(new Date());
        return fleaDao.save(flea);
    }

    @Override
    public Page<Flea> getFleaByContent(int pn, String content) {
        return fleaDao.findFleaByContent(pn, content);
    }
}
