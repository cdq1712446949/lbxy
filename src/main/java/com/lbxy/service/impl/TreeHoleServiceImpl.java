package com.lbxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
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

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
    public boolean deleteTreeHole(int id) {
        Treehole treeHole = new Treehole();
        treeHole.set("id", id);
        treeHole.set("status", CommonStatus.DELETED);
        return treeHoleDao.update(treeHole);
    }

    @Override
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

    @Override
    public JSONObject getMainByPage(int pn) {

        Page<Treehole> page = treeHoleDao.getMainByPage(pn);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject one = jsonArray.getJSONObject(i);

            //将用户信息放入结果集中
            one.put("userId", one.getIntValue("userId"));

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
    public boolean reply(long userId, ReplyBean replyBean) {
        Treehole treehole = new Treehole();
        treehole.setUserId(userId);
        treehole.setPId(replyBean.getpId());
        treehole.setPUserId(replyBean.getpUserId());
        treehole.setContent(replyBean.getContent());
        treehole.setPostDate(new Date());
        return treeHoleDao.save(treehole);
    }
}
