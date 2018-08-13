package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Order;

/**
 * @author lmy
 * @description OrderServiceImpl
 * @date 2018/8/14
 */
public interface OrderService {
    Order findById(int id);

    Page<Order> getUserOrder(int userid, int pn);

    void complete(int id);

    void accept(int id, int userId, String acceptUserPhoneNumber);

    void delete(int id);

    Page<Order> getAllOrder(int pn);

}
