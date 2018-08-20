package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Treehole;

public class TreeHoleDao {

    public Page<Treehole> findAllTreeHole(int pn) {
        return Treehole.dao.paginate(pn, 10, "select *", "from TreeHole");
    }

    public boolean update(Treehole treeHole) {
        boolean i = treeHole.update();
        return i;
    }
}
