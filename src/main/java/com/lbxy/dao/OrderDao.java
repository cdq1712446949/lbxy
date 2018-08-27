package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Order;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description OrderDao
 * @date 2018/8/18
 */
@Repository
public class OrderDao {

    public BigDecimal getWaitCompletedOrdersTotalRewardByAcceptUserId(int acceptUserId) {
        return Db.queryBigDecimal("select sum(reward) from `order` where acceptUserId=? and status=?", acceptUserId, OrderStatus.WAIT_COMPLETE);
    }

    public Page<Order> getUnCompletedOrdersByPage(int pn) {
        /*
        .createdDate,o.reward,o.userName,o.userPhoneNumber,o.fromAddress,o.toAddress,o.remark,o.detail
         */
        return Order.DAO.paginate(pn, PageConst.PAGE_SIZE, "select u.username,u.avatarUrl,o.*", " from `order` o inner join User u on o.userId = u.id where o.status=?", OrderStatus.UN_COMPLETED);
    }

    public Page<Order> findByPn(int pn) {
        return Order.DAO.paginate(pn, 10, "select *", " from `Order`");
    }

    public Order findById(int orderId) {
        return Order.DAO.findFirst("select u.username,u.avatarUrl,o.* from `order` o inner join user u on o.userId = u.id where o.id=?", orderId);
    }

    public Page<Order> findByUserId(int userId, int pn) {
        return Order.DAO.paginate(pn, PageConst.PAGE_SIZE, "select *", "from `order` where userId = ? and status != ?", userId, CommonStatus.DELETED);
    }

    public Page<Order> findByAcceptUserId(int acceptUserId, int pn) {
        return Order.DAO.paginate(pn, PageConst.PAGE_SIZE, "select *", "from `order` where acceptUserId = ? and status != ?", acceptUserId, CommonStatus.DELETED);
    }

    public int updateOrderStatus(int orderId, int status) {
        return Db.update("update `order` set status=? where id=?", status, orderId);
    }

    public boolean deleteById(int orderId) {
        return Order.DAO.deleteById(orderId);
    }
}
