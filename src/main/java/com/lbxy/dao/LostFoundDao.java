package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Lostfound;

public class LostFoundDao {

    public Page<Lostfound> findLostFounByPn(int pn){
        return Lostfound.dao.paginate(pn,10,"select *","from LostFound");
    }

    public boolean update(Lostfound lostFound){
        return  lostFound.update();
    }

}
