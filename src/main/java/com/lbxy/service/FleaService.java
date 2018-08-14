package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Flea;

public interface FleaService {

    Page<Flea> getAllFlea(int pn);

    boolean deleteFlea(int id);

}
