package com.lbxy.event.listeners;

import com.lbxy.core.plugins.cache.Injector;
import com.lbxy.event.CreateBillEvent;
import com.lbxy.event.UpdateBillEvent;
import com.lbxy.model.Bill;
import com.lbxy.service.BillService;
import net.dreamlu.event.core.EventListener;

import java.util.Date;

/**
 * @author lmy
 * @description BillListener
 * @date 2018/9/18
 */
public class BillListener {
    private final BillService billService = Injector.getInjector().getInstance(BillService.class);

    @EventListener(async = true)
    public void listenCreateBillEvent(CreateBillEvent event) {
        Bill bill = new Bill();
        bill.setOrderId(event.getOrderId());
        bill.setUserId(event.getUserId());
        bill.setMoney(event.getMoney());
        bill.setStatus(event.getStatus());
        bill.setCreatedDate(new Date());
        billService.add(bill);
    }

    @EventListener(async = true)
    public void listeneUpdateBillEvent(UpdateBillEvent even) {
        billService.updateStatusByUserIdAndOrderId(even.getUserId(), even.getOrderId(), even.getStatus());
    }
}
