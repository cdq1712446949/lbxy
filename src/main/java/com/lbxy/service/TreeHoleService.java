package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Treehole;

public interface TreeHoleService {

    Page<Treehole> getAllTreeHole(int pn);

    boolean deleteTreeHole(int id);



}
