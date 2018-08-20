package com.lbxy.dao;

import com.lbxy.model.Manager;

public class ManagerDao {

    public Manager findManagerByUserName(String username) {
        return Manager.dao.findFirst("select * from Manager where userName=?", username);
    }

}
