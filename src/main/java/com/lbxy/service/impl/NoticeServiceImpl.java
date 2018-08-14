package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.Status;
import com.lbxy.dao.NoticeDao;
import com.lbxy.model.Notice;
import com.lbxy.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {

    private NoticeDao noticeDao;

    public NoticeServiceImpl() {
        noticeDao = new NoticeDao();
    }

    @Override
    public Page<Notice> getAllNotice(int pn) {
        return noticeDao.findNoticeByPn(pn);
    }

    @Override
    public boolean deleteNotice(int id) {
        Notice notice=new Notice();
        notice.set("id",id);
        notice.set("status",Status.DELETED);
        return notice.update();
    }
}
