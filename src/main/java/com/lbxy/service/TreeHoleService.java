package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Treehole;

public interface TreeHoleService {

    Page<Treehole> getAllTreeHole(int pn);

    boolean deleteTreeHole(int id);

    long save(String content, long userId);

    Page<Treehole> getMainByPage(int pn);

    boolean reply(long userId, Long pId, Long pUserId, String content);
}
