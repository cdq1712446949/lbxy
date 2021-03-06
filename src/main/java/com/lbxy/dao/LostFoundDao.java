package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.model.Lostfound;

import java.util.List;

public class LostFoundDao {

    public LostFoundDao() {
    }

    public Page<Lostfound> findLostFoundByPn(int pn) {
        return Lostfound.DAO.paginate(pn, 10, "select *", "from lostfound");
    }

    public boolean update(Lostfound lostFound) {
        return lostFound.update();
    }

    public boolean save(Lostfound lostfound) {
        return lostfound.save();
    }

    public Page<Record> getMainByPage(int pn) {
        return Db.paginate(pn, PageConst.PAGE_SIZE, "select f.*,u.username,u.avatarUrl", "from lostfound f inner join user u on f.userId = u.id where f.pId is null and f.status = ? order by f.postDate desc", CommonStatus.NORMAL);
    }

    public List<Record> getReplyByPId(long pid) {
        return Db.find("select f.*,u.username from lostfound f inner join user u on f.userId = u.id where f.pId = ? and f.status = ? order by f.postDate asc", pid, CommonStatus.NORMAL);
    }

    public Lostfound getById(long id) {
        return Lostfound.DAO.findById(id);
    }

    public Page<Lostfound> findLostFoundByContent (int pn,String content){
        return Lostfound.DAO.paginate(pn,10,"select *","from lostfound where content like '%"+content+"%'");
    }

}
