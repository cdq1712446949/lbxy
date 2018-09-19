package com.lbxy.dao;

import com.lbxy.model.Manager;

public class ManagerDao {

    public ManagerDao() {
    }

    public Manager findManagerByUserName(String username) {
        return Manager.DAO.findFirst("select * from manager where userName=?", username);
    }

}
