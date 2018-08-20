package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.dao.LostFoundDao;
import com.lbxy.model.Lostfound;
import com.lbxy.service.LostFoundService;

public class LostFoundServiceImpl implements LostFoundService {

    private static final LostFoundDao lostFoundDao = new LostFoundDao();

    @Override
    public Page<Lostfound> getAllLostFound(int pn) {
        return lostFoundDao.findLostFounByPn(pn);
    }

    @Override
    public boolean deleteLostFound(int id) {
        Lostfound lostFound = new Lostfound();
        lostFound.set("id", id);
        lostFound.set("status", CommonStatus.DELETED);
        return lostFoundDao.update(lostFound);
    }
}
