package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.model.Order;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description OrderServiceImpl
 * @date 2018/8/14
 */
public interface OrderService {
    int SUCCESS = 0;
    int ERROR_USERID = 1;
    int ORDER_NOT_EXIST = 2;
    int NEED_MORE_INFO = 3;
    int CANT_ACCEPT_OWN_ORDER = 4;

    Order findById(long id);

    Page<Order> getOrdersByPage(int pn);

    Page<Order> getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(int pn);

    boolean complete(long id);

    int accept(long orderId, long userId);

    boolean delete(long id);

    Page<Order> getAllOrder(int pn);

    BigDecimal getWaitSettledReward(long acceptUserId);

    long createOrder(long userId, CreateOrderBean orderInfo);

    Page<Order> getOwnerPostOrders(int pn, long userId);

    Page<Order> getOwnerAcceptOrders(int pn, long userId);

    int cancelOrder(long orderId);

    boolean settleOrder(long orderId) throws Exception;

    boolean payOrder(long orderId);

    boolean updateModel(Order order);

    public boolean setPayBack(Order order);
}
