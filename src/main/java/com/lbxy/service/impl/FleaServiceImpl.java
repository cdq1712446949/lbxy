package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
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
}
