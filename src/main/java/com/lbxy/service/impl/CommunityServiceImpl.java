package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.Status;
import com.lbxy.model.Community;
import com.lbxy.service.CommunityService;

public class CommunityServiceImpl implements CommunityService {

    private Community dao;

    public CommunityServiceImpl() {
        dao = new Community().dao();
    }

    public Page<Community> getCommunity(int pn) {

        Page<Community> page = new Page<Community>();
        page = Community.dao.paginate(pn, 10, "select * ", " from Community");

        return page;
    }

    public boolean deleteById(int id) {
        Community community = dao.findById(id);
        community.set("status", Status.DELETED);
        Db.update("update Community set status = ? where pid = ?", 1, id);
        return community.update();
    }

}
