package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Db;
import com.lbxy.common.status.OrderStatus;

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
}
