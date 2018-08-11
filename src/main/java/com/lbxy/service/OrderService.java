package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.Status;
import com.lbxy.controller.OrderController;
import com.lbxy.model.Order;
import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService {

    private Order dao = new Order().dao();

    public Order findById(int id) {
        return Order.dao.findById(id);
    }

    public  Page<Order> getUserOrder(int userid,int pn) {
        List<Order> list = new ArrayList<Order>();
        Page<Order> page = dao.paginate(pn,10,"select *"," from `Order` where userId=?", userid);
        return page;
    }

    public void complete(int id){
        Order order=new Order();
        order.set("id",id);
        order.set("status",Status.COMPLETED);
        order.set("completedDate",new Date());
        order.update();
    }

    public void  accept(int id,int userId,String acceptUserPhoneNumber){
        Order order = new Order();
        order.set("id", id);
        order.set("status",Status.ACCEPTED);
        order.set("acceptUserId", userId);
        order.set("acceptUserPhoneNumber", acceptUserPhoneNumber);
        order.update();
    }

    public void delete(int id){
        Order order=new Order();
        order.set("id",id);
        order.set("status",Status.DELETED);
        order.update();
    }

}
