package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.ImageType;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.FleaDao;
import com.lbxy.dao.ImageDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Flea;
import com.lbxy.model.Image;
import com.lbxy.service.FleaService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("fleaService")
public class FleaServiceImpl implements FleaService {

    @Resource
    private FleaDao fleaDao;

    public FleaServiceImpl() {
        fleaDao = new FleaDao();
    }

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
    public Page<Flea> getMainByPage(int pn) {

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
                replyObject.put("pUsername", userDao.findById(replyObject.getIntValue("pUserId")).getUsername());
                replyObject.put("pUserId", userDao.findById(replyObject.getIntValue("pUserId")).getId());
            }

            one.put("reply", replyArray);

            // 将每一条的图片放入结果集
            List<Image> images = imageDao.getImagesByContentIdAndType(one.getIntValue("id"), ImageType.FLEA);
            one.put("images", images);
        }
        return fleaDao.getMainByPage(pn);
    }

    @Override
    public boolean reply(long userId, Long pId, Long pUserId, String content) {
        Flea flea = new Flea();
        flea.setUserId(userId);
        flea.setPId(pId);
        flea.setPUserId(pUserId);
        flea.setContent(content);
        flea.setPostDate(new Date());
        return fleaDao.save(flea);
    }
}
