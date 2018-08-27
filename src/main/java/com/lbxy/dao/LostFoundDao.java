package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Lostfound;

import java.util.List;

@Repository
public class LostFoundDao {

    public Page<Lostfound> findLostFounByPn(int pn){
        return Lostfound.dao.paginate(pn,10,"select *","from LostFound");
    }

    public boolean update(Lostfound lostFound){
        return  lostFound.update();
    }

    public boolean save(Lostfound lostfound) {
        return lostfound.save();
    }

    public Page<Lostfound> getMainByPage(int pn) {
        return Lostfound.dao.paginate(pn, PageConst.pageSize, "select f.*,u.username", "from lostfound f inner join user u on f.userId = u.id where f.pId is null order by f.postDate desc");
    }

    public List<Lostfound> getReplyByPId(int pid) {
        return Lostfound.dao.find("select f.*,u.username from lostfound f inner join user u on f.userId = u.id where f.pId = ? order by f.postDate asc", pid);
    }
}
