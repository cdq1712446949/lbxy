package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Community;

/**
 * @author lmy
 * @description CommunityServiceImpl
 * @date 2018/8/14
 */
public interface CommunityService {
    Page<Community> getCommunity(int pn);

    boolean deleteById(int id);
}
