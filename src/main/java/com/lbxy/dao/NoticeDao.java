package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Notice;

@Repository
public class NoticeDao {

    public boolean updata(Notice notice){
        return notice.update();
    }

    public boolean noticeEdit(Notice notice){
        return notice.update();
    }

    public boolean noticeSave(Notice notice){
        return notice.save();
    }

    public Page<Notice> findNoticeByPn(int pn){
        return Notice.DAO.paginate(pn,10,"select *","from Notice");
    }

    public Page<Notice> findNoticeByUserName(int pn,String userName){
        return Notice.DAO.paginate(pn,10,"select *","from Notice where userId=?",userName);
    }

}
