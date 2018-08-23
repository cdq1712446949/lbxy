package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.model.Community;
import com.lbxy.service.CommunityService;

import javax.annotation.Resource;

@Service("communityService")
public class CommunityServiceImpl implements CommunityService {

    private Community dao;

    public Page<Community> getCommunity(int pn) {

        Page<Community> page;
        page = Community.dao.paginate(pn, 10, "select * ", " from Community");

        return page;
    }

    public boolean deleteById(int id) {
        Community community = dao.findById(id);
        community.set("status", CommonStatus.DELETED);
        Db.update("update Community set status = ? where pid = ?", 1, id);
        return community.update();
    }

}
