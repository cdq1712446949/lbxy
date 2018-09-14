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
import com.lbxy.dao.FleaDao;
import com.lbxy.dao.ImageDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Flea;
import com.lbxy.model.Image;
import com.lbxy.model.User;
import com.lbxy.service.FleaService;
import com.lbxy.weixin.utils.WeixinUtil;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

        Page<Flea> page = fleaDao.getMainByPage(pn);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject one = jsonArray.getJSONObject(i);

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
    public JSONObject getMainById(long id) {

        Flea page = fleaDao.getById(id);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));

        //将用户信息放入结果集中
        User userInMain = userDao.findById(jsonObject.getIntValue("userId"));
        jsonObject.put("username", userInMain.getUsername());
        jsonObject.put("userId", userInMain.getId());
        jsonObject.put("avatarUrl", userInMain.getAvatarUrl());

        //将每一条回复放入结果集
        List<Flea> reply = fleaDao.getReplyByPId(jsonObject.getIntValue("id"));
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
        List<Image> images = imageDao.getImagesByContentIdAndType(jsonObject.getIntValue("id"), ImageType.FLEA);
        jsonObject.put("images", images);
        return jsonObject;
    }

    @Override
    @Before(Tx.class)
    public boolean reply(long userId, String formId, ReplyBean replyBean) {
        User currentUser = userDao.findById(userId);
        User toUser = userDao.findById(replyBean.getToUserId());
        Flea currentFlea = fleaDao.getById(replyBean.getpId());

        WeixinUtil.sendMessage(toUser.getOpenId(),
                formId,
                String.format("/pages/community/detail/detail?id=%s&type=0", replyBean.getpId()),
                "跳蚤市场",
                currentFlea.getContent(),
                currentUser.getUsername(),
                replyBean.getContent(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));

        if (Objects.nonNull(replyBean.getpUserId())) {
            User pUser = userDao.findById(replyBean.getpUserId());
            WeixinUtil.sendMessage(pUser.getOpenId(),
                    formId,
                    String.format("/pages/community/detail/detail?id=%s&type=0", replyBean.getpId()),
                    "跳蚤市场",
                    currentFlea.getContent(),
                    currentUser.getUsername(),
                    replyBean.getContent(),
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA)));
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
