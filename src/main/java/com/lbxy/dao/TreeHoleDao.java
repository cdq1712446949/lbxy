package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Treehole;
import sun.reflect.generics.tree.Tree;

import java.util.List;

@Repository
public class TreeHoleDao {

    public Page<Treehole> findAllTreeHole(int pn) {
        return Treehole.DAO.paginate(pn, 10, "select *", "from TreeHole");
    }

    public boolean update(Treehole treeHole) {
        boolean i = treeHole.update();
        return i;
    }

    public boolean save(Treehole treehole) {
        return treehole.save();
    }

    public Page<Treehole> getMainByPage(int pn) {
        return Treehole.DAO.paginate(pn, PageConst.PAGE_SIZE, "select f.*,u.username", "from treehole f inner join user u on f.userId = u.id where f.pId is null and f.status = ? order by f.postDate desc", CommonStatus.NORMAL);
    }

    public List<Treehole> getReplyByPId(long pid) {
        return Treehole.DAO.find("select f.*,u.username from treehole f inner join user u on f.userId = u.id where f.pId = ? and f.status = ? order by f.postDate asc", pid,CommonStatus.NORMAL);
    }

    public Treehole getById(long id) {
        return Treehole.DAO.findById(id);
    }

    public Page<Treehole> findTreeHoleByContent(int pn,String content){
        return Treehole.DAO.paginate(pn,10,"select *","from TreeHole where content like '%"+content+"%'");
    }

}


