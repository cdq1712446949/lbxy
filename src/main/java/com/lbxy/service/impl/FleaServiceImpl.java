package com.lbxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.ImageType;
import com.lbxy.common.request.ReplyBean;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.FleaDao;
import com.lbxy.dao.ImageDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Flea;
import com.lbxy.model.Image;
import com.lbxy.model.User;
import com.lbxy.service.FleaService;
import com.lbxy.weixin.utils.WeixinUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service("fleaService")
public class FleaServiceImpl implements FleaService {

    @Resource
    private FleaDao fleaDao;

    @Resource
    private UserDao userDao;

    @Resource
    private ImageDao imageDao;

    @Override
    public Page<Flea> getAllFlea(int pn) {
        return fleaDao.findFleaByPn(pn);
    }

    @Override
    public boolean deleteFlea(int id) {
        Flea flea = new Flea();
        flea.set("id", id);
        flea.set("status", CommonStatus.DELETED);
        return fleaDao.update(flea);
    }

    @Override
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

        Page<Flea> page = fleaDao.getMainByPage(pn);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject one = jsonArray.getJSONObject(i);
            //将用户信息放入结果集中
            User userInMain = userDao.findById(one.getIntValue("userId"));
            one.put("username", userInMain.getUsername());
            one.put("userId", userInMain.getId());
            one.put("avatarUrl", userInMain.getAvatarUrl());

            //将每一条回复放入结果集
            List<Flea> reply = fleaDao.getReplyByPId(one.getIntValue("id"));
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
            List<Image> images = imageDao.getImagesByContentIdAndType(one.getIntValue("id"), ImageType.FLEA);
            one.put("images", images);
        }

        return jsonObject;
    }

    @Override
    public boolean reply(long userId, ReplyBean replyBean) {
        //TODO 回复待完成， 微信要求Openid和formId一一对应，之后考虑采用采集formId的形式或者使用短信
//        User currentUser = userDao.findById(userId);
//        User toUser = userDao.findById(replyBean.getToUserId());

//        WeixinUtil.sendMessage(toUser.getOpenId(), replyBean.getFormId(), replyBean.getUrl(), currentUser.getUsername(), replyBean.getContent(), "");
//
//        if (Objects.nonNull(replyBean.getpUserId())) {
//            User pUser = userDao.findById(replyBean.getpUserId());
//             WeixinUtil.sendMessage(pUser.getOpenId(), replyBean.getFormId(), replyBean.getUrl(), currentUser.getUsername(), replyBean.getContent(), "");
//        }

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
        return fleaDao.findFleaByContent(pn,content);
    }
}
