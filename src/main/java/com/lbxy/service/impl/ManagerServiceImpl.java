package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Manager;
import com.lbxy.model.User;
import com.lbxy.service.ManagerService;
import com.lbxy.core.utils.PasswordUtil;

import java.util.List;

public class ManagerServiceImpl implements ManagerService {

    public static Manager MANAGER;

    public int  login(String username, String password) {
        int i=0;//0表示账号不存在，1表示密码不正确，2表示登陆成功
        List<Manager> list = Manager.dao.find("select * from Manager where userName=?", username);
        if (list.size()==0){
            System.out.println("账号不存在");
            return i;
        }else {
            String hash = list.get(0).getStr("passWord");
            if (  PasswordUtil.validatePassword(password, hash)){
                MANAGER=list.get(0);
                System.out.println("登陆成功");
                i=2;
            }else {
                System.out.println("密码错误");
                i=1;
            }
        }

        return i;

    }

    public Page<User> getUser(int pn){
        Page<User> page=User.dao.paginate(pn,10,"select *","from User");
        return page;
    }

}
