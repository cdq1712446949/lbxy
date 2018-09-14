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
import com.lbxy.core.annotation.Service;
import com.lbxy.core.utils.LoggerUtil;
import com.lbxy.dao.ImageDao;
import com.lbxy.dao.LostFoundDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Image;
import com.lbxy.model.Lostfound;
import com.lbxy.model.User;
import com.lbxy.service.LostFoundService;
import com.lbxy.weixin.utils.WeixinUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("lostFoundService")
public class LostFoundServiceImpl implements LostFoundService {

    @Resource
    private LostFoundDao lostFoundDao;

    @Resource
    private ImageDao imageDao;

    @Resource
    private UserDao userDao;

    @Override
    public Page<Lostfound> getAllLostFound(int pn) {
        return lostFoundDao.findLostFoundByPn(pn);
    }

    @Override
    @Before(Tx.class)
    public boolean deleteLostFound(int id) {
        Lostfound lostFound = new Lostfound();
        lostFound.set("id", id);
        lostFound.set("status", CommonStatus.DELETED);
        return lostFoundDao.update(lostFound);
    }

    @Override
    @Before(Tx.class)
    public long save(String content, long userId) {
        Lostfound lostfound = new Lostfound();
        lostfound.setUserId(userId);
        lostfound.setContent(content);
        lostfound.setPostDate(new Date());
        lostFoundDao.save(lostfound);
        return lostfound.getId();
    }

    @Override
    public JSONObject getMainByPage(int pn) {

        Page<Record> page = lostFoundDao.getMainByPage(pn);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject one = jsonArray.getJSONObject(i);

            //将每一条回复放入结果集
            List<Record> reply = lostFoundDao.getReplyByPId(one.getIntValue("id"));

            one.put("reply", reply);

            // 将每一条的图片放入结果集
            List<Image> images = imageDao.getImagesByContentIdAndType(one.getIntValue("id"), ImageType.LOSTFOUND);
            one.put("images", images);
        }
        return jsonObject;
    }

    public JSONObject getMainById(long id) {

        Lostfound page = lostFoundDao.getById(id);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        //将用户信息放入结果集中
        User userInMain = userDao.findById(jsonObject.getIntValue("userId"));
        jsonObject.put("username", userInMain.getUsername());
        jsonObject.put("userId", userInMain.getId());
        jsonObject.put("avatarUrl", userInMain.getAvatarUrl());

        //将每一条回复放入结果集
        List<Record> reply = lostFoundDao.getReplyByPId(jsonObject.getIntValue("id"));

        jsonObject.put("reply", reply);

        // 将每一条的图片放入结果集
        List<Image> images = imageDao.getImagesByContentIdAndType(jsonObject.getIntValue("id"), ImageType.LOSTFOUND);
        jsonObject.put("images", images);
        return jsonObject;
    }

    @Override
    @Before(Tx.class)
    public boolean reply(long userId, Optional<String> formId, ReplyBean replyBean) {
        User currentUser = userDao.findById(userId);
        User toUser = userDao.findById(replyBean.getToUserId());
        Lostfound currentLostfound = lostFoundDao.getById(replyBean.getpId());

        if (formId.isPresent()) {
            WeixinUtil.sendMessage(toUser.getOpenId(),
                    formId.get(),
                    String.format("/pages/community/detail/detail?id=%s&type=2", replyBean.getpId()),
                    "跳蚤市场",
                    currentLostfound.getContent(),
                    currentUser.getUsername(),
                    replyBean.getContent(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));

            if (Objects.nonNull(replyBean.getpUserId())) {
                User pUser = userDao.findById(replyBean.getpUserId());
                WeixinUtil.sendMessage(pUser.getOpenId(),
                        formId.get(),
                        String.format("/pages/community/detail/detail?id=%s&type=2", replyBean.getpId()),
                        "跳蚤市场",
                        currentLostfound.getContent(),
                        currentUser.getUsername(),
                        replyBean.getContent(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));
            }
        } else {
            LoggerUtil.info(getClass(), userId + "无可用formid");
        }


        Lostfound lostfound = new Lostfound();
        lostfound.setUserId(userId);
        lostfound.setPId(replyBean.getpId());
        lostfound.setPUserId(replyBean.getpUserId());
        lostfound.setContent(replyBean.getContent());
        lostfound.setPostDate(new Date());
        return lostFoundDao.save(lostfound);
    }

    @Override
    public Page<Lostfound> getLostFoundByContent(int pn, String content) {
        return lostFoundDao.findLostFoundByContent(pn, content);
    }
}
