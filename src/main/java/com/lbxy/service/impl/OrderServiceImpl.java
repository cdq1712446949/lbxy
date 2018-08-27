package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.OrderDao;
import com.lbxy.dao.UserDao;
import com.lbxy.model.Order;
import com.lbxy.model.User;
import com.lbxy.service.OrderService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private UserDao userDao;

    public OrderServiceImpl(){
        orderDao=new OrderDao();
        userDao=new UserDao();
    }

    @Override
    public Order findById(int id) {
        return orderDao.findById(id);
    }

    public Page<Order> getOrdersByPage(int pn) {
        return orderDao.getUnCompletedOrdersByPage(pn);
    }

    public boolean complete(int orderId) {
        Order order = orderDao.findById(orderId);
        order.setCompletedDate(new Date());
        order.setStatus(OrderStatus.COMPLETED);
        return order.update();
    }

    public int accept(int orderId, long userId) {
        User user = userDao.findById(userId);
        if (user == null) {
            return ERROR_USERID;
        }
        if (user.getPhoneNumber() == null || user.getReadName() == null) {
            return NEED_MORE_INFO;
        }

        Order order = orderDao.findById(orderId);
        if (order.getUserId() == userId) {
            return CANT_ACCEPT_OWN_ORDER;
        }
        order.setAcceptUserId(userId);
        order.setAcceptUserPhoneNumber(user.getPhoneNumber());
        order.setAcceptDate(new Date());
        order.setStatus(OrderStatus.WAIT_COMPLETE);
        order.update();
        return SUCCESS;
    }

    public Page<Order> getOwnerPostOrders(int pn, int userId) {
        return orderDao.findByUserId(userId, pn);
    }

    public Page<Order> getOwnerAcceptOrders(int pn, int userId) {
        return orderDao.findByAcceptUserId(userId, pn);
    }

    @Override
    public int cancelOrder(int orderId) {
        return orderDao.updateOrderStatus(orderId, OrderStatus.CANCELED);
    }

    @Override
    public int settleOrder(int orderId) throws Exception {
        Order order = orderDao.findById(orderId);
        long acceptUserId = order.getAcceptUserId();
        BigDecimal reward = order.getReward();

        User acceptUser = userDao.findById(acceptUserId);
        BigDecimal balance = acceptUser.getBalance();
        acceptUser.setBalance(balance.add(reward));
        boolean result = acceptUser.save();
        if (result) {
            return orderDao.updateOrderStatus(orderId, OrderStatus.SETTLED);
        } else {
            throw new Exception("订单结算失败，orderId：" + orderId);
        }
    }

    public boolean delete(int id) {
        return orderDao.deleteById(id);
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
