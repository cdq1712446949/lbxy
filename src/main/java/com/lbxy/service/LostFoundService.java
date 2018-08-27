package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Lostfound;

public interface LostFoundService {

    Page<Lostfound> getAllLostFound(int pn);

    boolean deleteLostFound(int id);

    long save(String content, long userId);

    Page<Lostfound> getMainByPage(int pn);

    boolean reply(long userId, Long pId, Long pUserId, String content);
}
