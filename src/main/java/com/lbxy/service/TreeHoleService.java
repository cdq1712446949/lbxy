package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.TreeHole;
import sun.security.krb5.internal.PAData;

public interface TreeHoleService {

    Page<TreeHole> getAllTreeHole(int pn);

    boolean deleteTreeHole(int id);

}
