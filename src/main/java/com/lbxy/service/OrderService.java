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
    Order findById(int id);

    Page<Order> getOrdersByPage(int pn);

    void complete(int id);

    void accept(int id, int userId, String acceptUserPhoneNumber);

    void delete(int id);

    Page<Order> getAllOrder(int pn);

    BigDecimal getWaitSettledReward(int acceptUserId);

    boolean createOrder(int userId, CreateOrderBean orderInfo);
}
