package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.model.Treehole;

import java.util.List;

public class TreeHoleDao {

    public TreeHoleDao() {
    }

    public Page<Treehole> findAllTreeHole(int pn) {
        return Treehole.DAO.paginate(pn, 10, "select *", "from treehole");
    }

    public boolean update(Treehole treeHole) {
        boolean i = treeHole.update();
        return i;
    }

    public boolean save(Treehole treehole) {
        return treehole.save();
    }

    public Page<Treehole> getMainByPage(int pn) {
        return Treehole.DAO.paginate(pn, PageConst.PAGE_SIZE, "select f.*", "from treehole f where f.pId is null and f.status = ? order by f.postDate desc", CommonStatus.NORMAL);
    }

    public List<Record> getReplyByPId(long pid) {
        return Db.find("select f.*,u.username from treehole f inner join user u on f.userId = u.id where f.pId = ? and f.status = ? order by f.postDate asc", pid,CommonStatus.NORMAL);
    }

    public Treehole getById(long id) {
        return Treehole.DAO.findById(id);
    }

    public Page<Treehole> findTreeHoleByContent(int pn,String content){
        return Treehole.DAO.paginate(pn,10,"select *","from treehole where content like '%"+content+"%'");
    }

}


