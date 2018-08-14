package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.dao.LostFounDao;
import com.lbxy.model.LostFound;
import com.lbxy.service.LostFoundService;

public class LostFoundServiceImpl implements LostFoundService {

    private LostFounDao lostFounDao;

    public LostFoundServiceImpl(){
        lostFounDao=new LostFounDao();
    }

    @Override
    public Page<LostFound> getAllLostFound(int pn) {
        return  lostFounDao.findLostFounByPn(pn);
    }
}
