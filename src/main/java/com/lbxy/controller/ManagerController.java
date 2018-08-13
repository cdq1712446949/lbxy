package com.lbxy.controller;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Order;
import com.lbxy.model.User;
import com.lbxy.service.ManagerService;
import com.lbxy.service.OrderService;
import com.lbxy.service.UserService;
import com.lbxy.service.impl.ManagerServiceImpl;
import com.lbxy.service.impl.OrderServiceImpl;
import com.lbxy.service.impl.UserServiceImpl;
import org.apache.commons.lang3.StringUtils;

public class ManagerController extends BaseController {

    private ManagerService managerService;

    private UserService userService;

    private OrderService orderService;

    public ManagerController() {
        userService = new UserServiceImpl();
        managerService = new ManagerServiceImpl();
        orderService =new OrderServiceImpl();
    }

    public void index() {
        setAttr("error", "");
        render("login.html");
    }

    public void userList() {
        int pn = 1;
        try {
            pn = getParaToInt("pn");
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<User> users = userService.getAllUsers(pn);
        setAttr("data", users);
        render("user_list.html");
    }

    public void search() {
        String phoneNum = getPara("phoneNumber");
        if (StringUtils.isBlank(phoneNum)) {
            Page<User> users = userService.getAllUsers(1);
            setAttr("data", users);
        } else {
            Page<User> user = userService.findByPhone(phoneNum);
            if (user.getList().size() == 0) {
                System.out.println("改手机号不存在");
                setAttr("error_search", "该手机号不存在");
            } else {
                setAttr("data", user);
            }
        }
        render("user_list.html");
    }

    public void login() {

        String username = getPara("username");
        String password = getPara("password");
        int i = managerService.login(username, password);
        if (i == 0) {
            setAttr("error", "账号不存在");
            return;
        }
        if (i == 1) {
            setAttr("error", "密码错误");
            render("login.html");
            return;
        }
        if (i == 2) {
            render("index.html");
            return;
        }
        System.out.println(username + password);
    }

    public void orderList(){
        int pn = 1;
        try {
            pn = getParaToInt("pn");
        } catch (Exception e) {
            System.out.println(" pageNumber is invalid");
        }
        Page<Order> orderPage = orderService.getAllOrder(pn);
        setAttr("orderpage", orderPage);
        render("order_list.html");
    }

}
