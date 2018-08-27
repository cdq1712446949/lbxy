package com.lbxy.dao;

import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Manager;

@Repository
public class ManagerDao {

    public Manager findManagerByUserName(String username) {
        return Manager.DAO.find("select * from Manager where userName=?", username).get(0);
    }

}
