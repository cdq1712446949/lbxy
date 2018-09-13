package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.status.BillStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.BillDao;
import com.lbxy.dao.OrderDao;
import com.lbxy.model.Bill;
import com.lbxy.service.PayBackService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lmy
 * @description PayBackServiceImpl
 * @date 2018/9/13
 */
@Service("payBackService")
public class PayBackServiceImpl implements PayBackService {

    @Resource
    private BillDao billDao;

    @Resource
    private OrderDao orderDao;

    @Override
    @Before(Tx.class)
    public void payBack(long orderId, long userId, double totalFee) {
        orderDao.updateOrderStatus(orderId, OrderStatus.UN_COMPLETED);

        Bill bill = new Bill();
        bill.setOrderId(orderId);
        bill.setUserId(userId);

        bill.setMoney(BigDecimal.valueOf(totalFee / 100));
        bill.setStatus(BillStatus.PAY);
        bill.setCreatedDate(new Date());

        billDao.save(bill);
    }
}
