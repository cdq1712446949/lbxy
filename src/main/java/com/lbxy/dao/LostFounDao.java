package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.LostFound;

public class LostFounDao {

    public Page<LostFound> findLostFounByPn(int pn){
        return LostFound.dao.paginate(pn,10,"select *","from LostFound");
    }

}
