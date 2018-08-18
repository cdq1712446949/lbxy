package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.dao.OrderDao;
import com.lbxy.model.Order;
import com.lbxy.service.OrderService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private Order dao = new Order().dao();

    public OrderServiceImpl() {
        orderDao = new OrderDao();
    }

    public Order findById(int id) {
        return Order.dao.findById(id);
    }

    public Page<Order> getUserOrder(int userId, int pn) {
        List<Order> list = new ArrayList<Order>();
        Page<Order> page = dao.paginate(pn, 10, "select *", " from `Order` where userId=?", userId);
        return page;
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
        return Order.dao.paginate(pn, 10, "select *", " from `Order`");
    }

    public BigDecimal getWaitSettledReward(int acceptUserId) {
        return orderDao.getWaitCompletedOrdersTotalRewardByAcceptUserId(acceptUserId);
    }
}
