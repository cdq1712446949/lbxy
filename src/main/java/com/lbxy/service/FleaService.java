package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Flea;

public interface FleaService {

    Page<Flea> getAllFlea(int pn);

    boolean deleteFlea(int id);

    long save(String content,long userId);

    Page<Flea> getMainByPage(int pn);

    boolean reply(long userId,Long pId, Long pUserId, String content);
}
