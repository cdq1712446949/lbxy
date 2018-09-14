package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Flea;

import java.util.List;

@Repository
public class FleaDao {

    public Page<Flea> findFleaByPn(int pn) {
        return Flea.DAO.paginate(pn, PageConst.PAGE_SIZE, "select *", "from flea");
    }

    public boolean update(Flea flea) {
        return flea.update();
    }

    public boolean save(Flea flea) {
        return flea.save();
    }

    public Page<Flea> getMainByPage(int pn) {
        return Flea.DAO.paginate(pn, PageConst.PAGE_SIZE, "select f.*,u.username,u.avatarUrl", "from flea f inner join user u on f.userId = u.id where f.pId is null and f.status = ? order by f.postDate desc", CommonStatus.NORMAL);
    }

    public List<Flea> getReplyByPId(long pid) {
        return Flea.DAO.find("select f.*,u.username from flea f inner join user u on f.userId = u.id where f.pId = ? and f.status = ? order by f.postDate asc", pid, CommonStatus.NORMAL);
    }

    public Flea getById(long id) {
        return Flea.DAO.findById(id);
    }

    public Page<Flea> findFleaByContent(int pn,String content){
        return Flea.DAO.paginate(pn,10,"select *","from flea where content like '%"+content+"%'");
    }

}
