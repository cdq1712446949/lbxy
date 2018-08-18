package com.lbxy.dao;

import com.lbxy.model.Manager;

import java.util.List;

public class ManagerDao {

    public List<Manager> findManagerByUserName(String username){
        return Manager.dao.find("select * from Manager where userName=?",username);
    }

}
