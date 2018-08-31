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

    Order findById(int id);

    Page<Order> getOrdersByPage(int pn);

    Page<Order> getUnCompletedAndWaitCompletedOrdersByPage(int pn);

    boolean complete(int id);

    int accept(int orderId, long userId);

    boolean delete(int id);

    Page<Order> getAllOrder(int pn);

    BigDecimal getWaitSettledReward(int acceptUserId);

    long createOrder(long userId, CreateOrderBean orderInfo);

    Page<Order> getOwnerPostOrders(int pn, int userId);

    Page<Order> getOwnerAcceptOrders(int pn, int userId);

    int cancelOrder(int orderId);

    int settleOrder(int orderId) throws Exception;

    boolean payOrder(int orderId);
}
