package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.NoticeDao;
import com.lbxy.model.Notice;
import com.lbxy.service.NoticeService;

import java.util.Date;

import javax.annotation.Resource;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeDao noticeDao;

    @Override
    public Page<Notice> getAllNotice(int pn) {
        return noticeDao.findNoticeByPn(pn);
    }

    @Override
    @Before(Tx.class)
    public boolean deleteNotice(int id) {
        Notice notice=new Notice();
        notice.set("id",id);
        notice.set("status", CommonStatus.DELETED);
        return notice.update();
    }

    @Override
    public Page<Notice> findByUserName(int pn,String userNmae) {
        return noticeDao.findNoticeByUserName(pn,userNmae);
    }

    @Override
    @Before(Tx.class)
    public boolean noticeEdit(int id, String content, String title) {
        Notice notice=new Notice();
        notice.set("id",id);
        notice.set("content",content);
        notice.set("title",title);
        return noticeDao.noticeEdit(notice);
    }

    @Override
    @Before(Tx.class)
    public boolean noticeSave(String userId,String content, String title) {
        Notice notice=new Notice();
        notice.set("userId",userId);
        notice.set("content",content);
        notice.set("title",title);
        notice.set("postDate",new Date());
        return noticeDao.noticeSave(notice);
    }


}
