package com.lbxy.service.cron;

import com.lbxy.service.OrderService;
import com.lbxy.service.impl.OrderServiceImpl;

/**
 * @author lmy
 * @description OrderCronTask order定时任务
 * @date 2018/8/19
 */
public class OrderCronTask implements Runnable {

    private  OrderService orderService = new OrderServiceImpl();

    @Override
    public void run() {

    }
}
