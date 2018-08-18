package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.dao.TreeHoleDao;
import com.lbxy.model.TreeHole;
import com.lbxy.service.TreeHoleService;

public class TreeHoleServiceImpl implements TreeHoleService {

    private TreeHoleDao treeHoleDao;

    public TreeHoleServiceImpl(){
        treeHoleDao=new TreeHoleDao();
    }

    @Override
    public Page<TreeHole> getAllTreeHole(int pn) {
        return treeHoleDao.findAllTreeHole(pn);
    }

    @Override
    public boolean deleteTreeHole(int id) {
        TreeHole treeHole=new TreeHole();
        treeHole.set("id",id);
        treeHole.set("status", CommonStatus.DELETED);
        return treeHoleDao.update(treeHole);
    }


}
