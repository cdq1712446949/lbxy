package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.model.Order;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description OrderDao
 * @date 2018/8/18
 */
public class OrderDao {

    public OrderDao() {
    }

    public int getTotalNumber() {
        return Db.queryInt("select count(*) from `order`");
    }

    public Page<Order> getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(int pn) {
        return Order.DAO.paginate(pn, PageConst.PAGE_SIZE, "select * ", "from `order` where status=? or status=? or status = ?", OrderStatus.UN_COMPLETED, OrderStatus.WAIT_COMPLETE, OrderStatus.COMPLETED);
    }

    public BigDecimal getWaitCompletedOrdersTotalRewardByAcceptUserId(long acceptUserId) {
        return Db.queryBigDecimal("select sum(reward) from `order` where acceptUserId=? and status=?", acceptUserId, OrderStatus.WAIT_COMPLETE);
    }

    public Page<Record> getUnCompletedOrdersByPage(int pn) {
        /*
        .createdDate,o.reward,o.userName,o.userPhoneNumber,o.fromAddress,o.toAddress,o.remark,o.detail
         */
        return Db.paginate(pn, PageConst.PAGE_SIZE, "select u.username,u.avatarUrl,o.*", " from `order` o inner join user u on o.userId = u.id where o.status=? order by o.createdDate desc", OrderStatus.UN_COMPLETED);
    }

    public Page<Order> findByPn(int pn) {
        return Order.DAO.paginate(pn, 10, "select *", " from `order` order by createdDate desc");
    }

    public Order findById(long orderId) {
        return Order.DAO.findFirst("select o.* from `order` o where o.id=?", orderId);
    }

    public Page<Order> findByUserId(long userId, int pn) {
        return Order.DAO.paginate(pn, PageConst.PAGE_SIZE, "select *", "from `order` where userId = ? and status != ? order by createdDate desc", userId, CommonStatus.DELETED);
    }

    public Page<Order> findByAcceptUserId(long acceptUserId, int pn) {
        return Order.DAO.paginate(pn, PageConst.PAGE_SIZE, "select *", "from `order` where acceptUserId = ? and status != ? order by createdDate desc", acceptUserId, CommonStatus.DELETED);
    }

    public int updateOrderStatus(long orderId, int status) {
        return Db.update("update `order` set status=? where id=?", status, orderId);
    }

    public boolean deleteById(long orderId) {
        return Order.DAO.deleteById(orderId);
    }

    public boolean update(Order order) {
        return order.update();
    }

    public boolean save(Order order) {
        return order.save();
    }
}
