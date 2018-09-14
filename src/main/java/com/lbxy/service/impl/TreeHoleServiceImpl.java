package com.lbxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.ImageType;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Service;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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

        //将每一条回复放入结果集
        List<Treehole> reply = treeHoleDao.getReplyByPId(id);
        JSONArray replyArray = JSON.parseArray(JSON.toJSONString(reply));
        for (int i1 = 0; i1 < replyArray.size(); i1++) {
            // 将回复中的被回复者的username放入结果集
            JSONObject replyObject = replyArray.getJSONObject(i1);
            User userInReply = userDao.findById(replyObject.getIntValue("userId"));
            replyObject.put("username", userInReply.getUsername());
            replyObject.put("userId", userInReply.getId());
        }
        jsonObject.put("reply", replyArray);

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

            //将用户信息放入结果集中
            User userInMain = userDao.findById(one.getIntValue("userId"));
            one.put("avatarUrl", userInMain.getAvatarUrl());
            one.put("username", userInMain.getUsername());

            //将每一条回复放入结果集
            List<Treehole> reply = treeHoleDao.getReplyByPId(one.getIntValue("id"));
            JSONArray replyArray = JSON.parseArray(JSON.toJSONString(reply));
            for (int i1 = 0; i1 < replyArray.size(); i1++) {
                // 将回复中的被回复者的username放入结果集
                JSONObject replyObject = replyArray.getJSONObject(i1);
                User userInReply = userDao.findById(replyObject.getIntValue("userId"));
                replyObject.put("username", userInReply.getUsername());
                replyObject.put("userId", userInReply.getId());
            }
            one.put("reply", replyArray);

            // 将每一条的图片放入结果集
            List<Image> images = imageDao.getImagesByContentIdAndType(one.getIntValue("id"), ImageType.TREEHOLE);
            one.put("images", images);
        }
        return jsonObject;
    }

    @Override
    @Before(Tx.class)
    public boolean reply(long userId, String formId, ReplyBean replyBean) {
        User currentUser = userDao.findById(userId);
        User toUser = userDao.findById(replyBean.getToUserId());
        Treehole currentTreehole = treeHoleDao.getById(replyBean.getpId());

        WeixinUtil.sendMessage(toUser.getOpenId(),
                formId,
                String.format("/pages/community/detail/detail?id=%s&type=1", replyBean.getpId()),
                "跳蚤市场",
                currentTreehole.getContent(),
                currentUser.getUsername(),
                replyBean.getContent(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));

        if (Objects.nonNull(replyBean.getpUserId())) {
            User pUser = userDao.findById(replyBean.getpUserId());
            WeixinUtil.sendMessage(pUser.getOpenId(),
                    formId,
                    String.format("/pages/community/detail/detail?id=%s&type=1", replyBean.getpId()),
                    "跳蚤市场",
                    currentTreehole.getContent(),
                    currentUser.getUsername(),
                    replyBean.getContent(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));
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
