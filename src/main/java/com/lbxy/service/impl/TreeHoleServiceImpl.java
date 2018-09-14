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
import com.lbxy.core.utils.RandomAvatarUtil;
import com.lbxy.core.utils.RandomColorUtil;
import com.lbxy.dao.ImageDao;
import com.lbxy.dao.TreeHoleDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Image;
import com.lbxy.model.Treehole;
import com.lbxy.model.User;
import com.lbxy.service.TreeHoleService;
import com.lbxy.weixin.utils.WeixinUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service("treeHoleService")
public class TreeHoleServiceImpl implements TreeHoleService {

    @Resource
    private TreeHoleDao treeHoleDao;

    @Resource
    private ImageDao imageDao;

    @Resource
    private UserDao userDao;

    @Override
    public Page<Treehole> getAllTreeHole(int pn) {
        return treeHoleDao.findAllTreeHole(pn);
    }

    @Override
    @Before(Tx.class)
    public boolean deleteTreeHole(int id) {
        Treehole treeHole = new Treehole();
        treeHole.set("id", id);
        treeHole.set("status", CommonStatus.DELETED);
        return treeHoleDao.update(treeHole);
    }

    @Override
    @Before(Tx.class)
    public long save(String content, long userId) {
        Treehole treehole = new Treehole();
        treehole.setUserId(userId);
        treehole.setContent(content);
        treehole.setPostDate(new Date());
        treehole.setAvatarUrl(RandomAvatarUtil.generateAvatarUrl());
        treehole.setNameColor(RandomColorUtil.generateRandomHex());
        treeHoleDao.save(treehole);
        return treehole.getId();
    }

    public JSONObject getMainById(long id) {
        Treehole page = treeHoleDao.getById(id);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));

        //将用户信息放入结果集中
        User userInMain = userDao.findById(jsonObject.getIntValue("userId"));
        jsonObject.put("username", userInMain.getUsername());
        jsonObject.put("userId", userInMain.getId());

        //将每一条回复放入结果集
        List<Record> reply = treeHoleDao.getReplyByPId(id);
        jsonObject.put("reply", reply);

        // 将每一条的图片放入结果集
        List<Image> images = imageDao.getImagesByContentIdAndType(id, ImageType.TREEHOLE);
        jsonObject.put("images", images);
        return jsonObject;
    }

    @Override
    public JSONObject getMainByPage(int pn) {

        Page<Treehole> page = treeHoleDao.getMainByPage(pn);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject one = jsonArray.getJSONObject(i);

            //将每一条回复放入结果集
            List<Record> reply = treeHoleDao.getReplyByPId(one.getIntValue("id"));
            one.put("reply", reply);

            // 将每一条的图片放入结果集
            List<Image> images = imageDao.getImagesByContentIdAndType(one.getIntValue("id"), ImageType.TREEHOLE);
            one.put("images", images);
        }
        return jsonObject;
    }

    @Override
    @Before(Tx.class)
    public boolean reply(long userId, Optional<String> formId, ReplyBean replyBean) {
        User currentUser = userDao.findById(userId);
        User toUser = userDao.findById(replyBean.getToUserId());
        Treehole currentTreehole = treeHoleDao.getById(replyBean.getpId());

        if (formId.isPresent()) {
            WeixinUtil.sendMessage(toUser.getOpenId(),
                    formId.get(),
                    String.format("/pages/community/detail/detail?id=%s&type=1", replyBean.getpId()),
                    "跳蚤市场",
                    currentTreehole.getContent(),
                    currentUser.getUsername(),
                    replyBean.getContent(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));

            if (Objects.nonNull(replyBean.getpUserId())) {
                User pUser = userDao.findById(replyBean.getpUserId());
                WeixinUtil.sendMessage(pUser.getOpenId(),
                        formId.get(),
                        String.format("/pages/community/detail/detail?id=%s&type=1", replyBean.getpId()),
                        "跳蚤市场",
                        currentTreehole.getContent(),
                        currentUser.getUsername(),
                        replyBean.getContent(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));
            }
        } else {
            LoggerUtil.info(getClass(), userId + "无可用formid");
        }

        Treehole treehole = new Treehole();
        treehole.setUserId(userId);
        treehole.setPId(replyBean.getpId());
        treehole.setPUserId(replyBean.getpUserId());
        treehole.setContent(replyBean.getContent());
        treehole.setPostDate(new Date());

        return treeHoleDao.save(treehole);
    }

    @Override
    public Page<Treehole> getTreeHoleByContent(int pn, String content) {
        return treeHoleDao.findTreeHoleByContent(pn, content);
    }
}
