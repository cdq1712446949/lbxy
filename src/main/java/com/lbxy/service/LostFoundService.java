package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.LostFound;

public interface LostFoundService {

    Page<LostFound> getAllLostFound(int pn);

    boolean deleteLostFound(int id);

}
