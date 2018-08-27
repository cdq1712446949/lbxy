package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Notice;

public interface NoticeService {

    Page<Notice> getAllNotice(int pn);

    boolean deleteNotice(int id);

    Page<Notice> findByUserName(int pn,String userNmae);

    boolean noticeEdit(int id,String content,String title);

    boolean noticeSave(String userId,String content ,String title);

}
