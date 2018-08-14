package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Flea;

public class FleaDao {

    public Page<Flea> findFleaByPn(int pn){
        return Flea.dao.paginate(pn,10,"select *","from Flea");
    }

    public boolean update(Flea flea){
        return flea.update();
    }

}
