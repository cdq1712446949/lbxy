package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Notice;

public class NoticeDao {

    public boolean updata(Notice notice){
        return notice.update();
    }

    public boolean noticeEdit(Notice notice){
        return notice.update();
    }

    public Page<Notice> findNoticeByPn(int pn){
        return Notice.dao.paginate(pn,10,"select *","from Notice");
    }

    public Page<Notice> findNoticeByUserName(int pn,String userName){
        return Notice.dao.paginate(pn,10,"select *","from Notice where userId=?",userName);
    }

}
