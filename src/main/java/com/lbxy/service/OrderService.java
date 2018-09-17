package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.model.Order;
import com.lbxy.model.User;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description OrderServiceImpl
 * @date 2018/8/14
 */
public interface OrderService {

    Order findById(long id);

    Page<Record> getOrdersByPage(int pn);

    Page<Order> getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(int pn);

    boolean complete(long id);

    boolean updateAcceptOrder(long orderId, User user) throws Exception;

    boolean delete(long id);

    Page<Order> getAllOrder(int pn);

    BigDecimal getWaitSettledReward(long acceptUserId);

    long createOrder(long userId, CreateOrderBean orderInfo);

    Page<Order> getOwnerPostOrders(int pn, long userId);

    Page<Order> getOwnerAcceptOrders(int pn, long userId);

    boolean cancelOrder(long orderId);

    boolean settleOrder(long orderId) throws Exception;

    boolean payOrder(long orderId);

    boolean updateModel(Order order);

    boolean setPayBack(Order order);
}
