package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Lostfound;

import java.util.List;

@Repository
public class LostFoundDao {

    public Page<Lostfound> findLostFoundByPn(int pn) {
        return Lostfound.DAO.paginate(pn, 10, "select *", "from lostfound");
    }

    public boolean update(Lostfound lostFound) {
        return lostFound.update();
    }

    public boolean save(Lostfound lostfound) {
        return lostfound.save();
    }

    public Page<Lostfound> getMainByPage(int pn) {
        return Lostfound.DAO.paginate(pn, PageConst.PAGE_SIZE, "select f.*,u.username", "from lostfound f inner join user u on f.userId = u.id where f.pId is null and f.status = ? order by f.postDate desc", CommonStatus.NORMAL);
    }

    public List<Lostfound> getReplyByPId(long pid) {
        return Lostfound.DAO.find("select f.*,u.username from lostfound f inner join user u on f.userId = u.id where f.pId = ? and f.status = ? order by f.postDate asc", pid, CommonStatus.NORMAL);
    }

    public Lostfound getById(long id) {
        return Lostfound.DAO.findById(id);
    }

    public Page<Lostfound> findLostFoundByContent (int pn,String content){
        return Lostfound.DAO.paginate(pn,10,"select *","from lostfound where content like '%"+content+"%'");
    }

}
