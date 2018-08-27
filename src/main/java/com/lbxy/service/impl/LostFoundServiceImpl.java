package com.lbxy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.ImageType;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.ImageDao;
import com.lbxy.dao.LostFoundDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Image;
import com.lbxy.model.Lostfound;
import com.lbxy.service.LostFoundService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("lostFoundService")
public class LostFoundServiceImpl implements LostFoundService {

    @Resource
    private LostFoundDao lostFoundDao;

    public LostFoundServiceImpl(){
        lostFoundDao=new LostFoundDao();
    }

    @Resource
    private ImageDao imageDao;

    @Resource
    private UserDao userDao;

    @Override
    public Page<Lostfound> getAllLostFound(int pn) {
        return lostFoundDao.findLostFounByPn(pn);
    }

    @Override
    public boolean deleteLostFound(int id) {
        Lostfound lostFound = new Lostfound();
        lostFound.set("id", id);
        lostFound.set("status", CommonStatus.DELETED);
        return lostFoundDao.update(lostFound);
    }

    @Override
    public long save(String content, long userId) {
        Lostfound lostfound = new Lostfound();
        lostfound.setUserId(userId);
        lostfound.setContent(content);
        lostfound.setPostDate(new Date());
        lostFoundDao.save(lostfound);
        return lostfound.getId();
    }

    @Override
    public Page<Lostfound> getMainByPage(int pn) {

        Page<Lostfound> page = lostFoundDao.getMainByPage(pn);
        JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(page));
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject one = jsonArray.getJSONObject(i);

            //将每一条回复放入结果集
            List<Lostfound> reply = lostFoundDao.getReplyByPId(one.getIntValue("id"));
            JSONArray replyArray = JSON.parseArray(JSON.toJSONString(reply));
            for (int i1 = 0; i1 < replyArray.size(); i1++) {
                // 将回复中的被回复者的username放入结果集
                JSONObject replyObject = replyArray.getJSONObject(i1);
                replyObject.put("pUsername", userDao.findById(replyObject.getIntValue("pUserId")).getUsername());
                replyObject.put("pUserId", userDao.findById(replyObject.getIntValue("pUserId")).getId());
            }

            one.put("reply", replyArray);

            // 将每一条的图片放入结果集
            List<Image> images = imageDao.getImagesByContentIdAndType(one.getIntValue("id"), ImageType.LOSTFOUND);
            one.put("images", images);
        }
        return lostFoundDao.getMainByPage(pn);
    }

    @Override
    public boolean reply(long userId, Long pId, Long pUserId, String content) {
        Lostfound lostfound = new Lostfound();
        lostfound.setUserId(userId);
        lostfound.setPId(pId);
        lostfound.setPUserId(pUserId);
        lostfound.setContent(content);
        lostfound.setPostDate(new Date());
        return lostFoundDao.save(lostfound);
    }
}
