package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.model.Order;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description OrderDao
 * @date 2018/8/18
 */
public class OrderDao {

    public BigDecimal getWaitCompletedOrdersTotalRewardByAcceptUserId(int acceptUserId) {
        return Db.queryBigDecimal("select sum(reward) from `Order` where acceptUserId=? and status=?", acceptUserId, OrderStatus.WAIT_COMPLETE);
    }

    public Page<Order> getOrdersByPage(int pn) {
        /*
        .createdDate,o.reward,o.userName,o.userPhoneNumber,o.fromAddress,o.toAddress,o.remark,o.detail
         */
        return Order.dao.paginate(pn, PageConst.pageSize, "select u.username,u.avatarUrl,o", " from `Order` o inner join User u on o.userId = u.id");
    }

    public Page<Order> findByPn( int pn ){
        return Order.dao.paginate(pn, 10, "select *", " from `Order`");
    }

}
