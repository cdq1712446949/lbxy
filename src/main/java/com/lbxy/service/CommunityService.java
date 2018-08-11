package com.lbxy.service;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lbxy.common.Status;
import com.lbxy.model.Community;

import java.util.ArrayList;
import java.util.List;

import static com.lbxy.model.Bill.dao;

public class CommunityService {

    private Community dao;

    public CommunityService() {
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
