package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Treehole;

import java.util.List;

@Repository
public class TreeHoleDao {

    public Page<Treehole> findAllTreeHole(int pn) {
        return Treehole.dao.paginate(pn, 10, "select *", "from TreeHole");
    }

    public boolean update(Treehole treeHole) {
        boolean i = treeHole.update();
        return i;
    }
    public boolean save(Treehole treehole) {
        return treehole.save();
    }

    public Page<Treehole> getMainByPage(int pn) {
        return Treehole.dao.paginate(pn, PageConst.pageSize, "select f.*,u.username", "from treehole f inner join user u on f.userId = u.id where f.pId is null order by f.postDate desc");
    }

    public List<Treehole> getReplyByPId(int pid) {
        return Treehole.dao.find("select f.*,u.username from treehole f inner join user u on f.userId = u.id where f.pId = ? order by f.postDate asc", pid);
    }
}


