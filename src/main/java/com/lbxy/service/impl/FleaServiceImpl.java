package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.Status;
import com.lbxy.dao.FleaDao;
import com.lbxy.model.Flea;
import com.lbxy.service.FleaService;

public class FleaServiceImpl implements FleaService {

    private FleaDao fleaDao;

    public FleaServiceImpl(){
        fleaDao=new FleaDao();
    }

    @Override
    public Page<Flea> getAllFlea(int pn) {
        return fleaDao.findFleaByPn(pn);
    }

    @Override
    public boolean deleteFlea(int id) {
        Flea flea=new Flea();
        flea.set("id",id);
        flea.set("status",Status.DELETED);
        return  fleaDao.update(flea);
    }
}
