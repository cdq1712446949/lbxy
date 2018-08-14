package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.TreeHole;
import sun.reflect.generics.tree.Tree;

public class TreeHoleDao {

    public Page<TreeHole> findAllTreeHole(int pn) {
        return TreeHole.dao.paginate(pn, 10, "select *", "from TreeHole");
    }

    public boolean update(TreeHole treeHole) {
        boolean i = treeHole.update();
        return i;
    }
}
