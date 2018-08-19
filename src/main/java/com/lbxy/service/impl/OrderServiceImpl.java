package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.dao.OrderDao;
import com.lbxy.model.Order;
import com.lbxy.service.OrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;

    public OrderServiceImpl() {
        orderDao = new OrderDao();
    }

    public Order findById(int id) {
        return Order.dao.findById(id);
    }

    public Page<Order> getOrdersByPage(int pn) {
        return orderDao.getOrdersByPage(pn);
    }

    public void complete(int id) {
        Order order = new Order();
        order.set("id", id);
        order.set("status", OrderStatus.COMPLETED);
        order.set("completedDate", new Date());
        order.update();
    }

    public void accept(int id, int userId, String acceptUserPhoneNumber) {
        Order order = new Order();
        order.set("id", id);
        order.set("status", OrderStatus.WAIT_COMPLETE);
        order.set("acceptUserId", userId);
        order.set("acceptUserPhoneNumber", acceptUserPhoneNumber);
        order.update();
    }

    public void delete(int id) {
        Order order = new Order();
        order.set("id", id);
        order.set("status", CommonStatus.DELETED);
        order.update();
    }

    public Page<Order> getAllOrder(int pn) {
        int totalNum = Db.queryInt("select count(*) from `Order`");
        int totalPage = totalNum / 10;
        if (totalNum % 10 >= 1) {
            totalPage += 1;
        }
        if (pn >= totalPage) {
            pn = totalPage;
        }

        if (pn <= 1) {
            pn = 1;
        }
        return orderDao.findByPn(pn);
    }

    public BigDecimal getWaitSettledReward(int acceptUserId) {
        return orderDao.getWaitCompletedOrdersTotalRewardByAcceptUserId(acceptUserId);
    }

    @Override
    public boolean createOrder(int userId, CreateOrderBean orderInfo) {
        Order order = new Order();
        order.set("createdDate", LocalDateTime.now());
        order.set("reward", orderInfo.getReward());
        order.set("userId", userId);
        order.set("userName", orderInfo.getUserName());
        order.set("userPhoneNumber", orderInfo.getUserPhoneNumber());
        order.set("fromAddress", orderInfo.getFromAddress());
        order.set("toAddress", orderInfo.getToAddress());
        order.set("remark", orderInfo.getReward());
        order.set("detail", orderInfo.getDetail());
        order.set("availableDate", orderInfo.getAvailableData());

        return order.save();
    }
}
