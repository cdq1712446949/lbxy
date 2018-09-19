package com.lbxy.service.impl;

import com.lbxy.core.utils.PasswordUtil;
import com.lbxy.dao.ManagerDao;
import com.lbxy.model.Manager;
import com.lbxy.service.ManagerService;

import javax.inject.Inject;
public class ManagerServiceImpl implements ManagerService {

    private final ManagerDao managerDao;

    @Inject
    public ManagerServiceImpl(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    public int login(String username, String password) {
        Manager manager = managerDao.findManagerByUserName(username);
        if (manager == null) {
            System.out.println("账号不存在");
            return ManagerService.NOT_EXIST;
        } else {
            String hash = manager.getPassWord();
            if (PasswordUtil.validatePassword(password, hash)) {
                System.out.println("登陆成功");
                return ManagerService.SUCCESS;
            } else {
                System.out.println("密码错误");
                return ManagerService.INVALID_PASSWORD;
            }
        }
    }

    @Override
    public Manager getManager(String username) {
        Manager manager = managerDao.findManagerByUserName(username);
        return manager;
    }

}
