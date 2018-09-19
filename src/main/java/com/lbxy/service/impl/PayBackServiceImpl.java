package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.status.BillStatus;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.event.CreateBillEvent;
import com.lbxy.event.UpdateOrderStatusEvent;
import com.lbxy.service.PayBackService;
import net.dreamlu.event.EventKit;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description PayBackServiceImpl
 * @date 2018/9/13
 */
public class PayBackServiceImpl implements PayBackService {

    @Override
    @Before(Tx.class)
    public void payBack(long orderId, long userId, double totalFee) {
        EventKit.post(new UpdateOrderStatusEvent(getClass(), orderId, OrderStatus.UN_COMPLETED));

        EventKit.post(new CreateBillEvent(getClass(), orderId, userId, BigDecimal.valueOf(totalFee / 100), BillStatus.PAY));
    }
}
