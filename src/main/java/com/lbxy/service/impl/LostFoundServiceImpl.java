package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.LostFoundDao;
import com.lbxy.model.Lostfound;
import com.lbxy.service.LostFoundService;

import javax.annotation.Resource;

@Service("lostFoundService")
public class LostFoundServiceImpl implements LostFoundService {

    @Resource
    private LostFoundDao lostFoundDao;

    public LostFoundServiceImpl(){
        lostFoundDao=new LostFoundDao();
    }

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
