package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Lostfound;

public interface LostFoundService {

    Page<Lostfound> getAllLostFound(int pn);

    boolean deleteLostFound(int id);

}
