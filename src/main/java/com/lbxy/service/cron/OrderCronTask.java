package com.lbxy.service.cron;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.status.OrderStatus;
import com.lbxy.model.Order;
import com.lbxy.service.OrderService;
import com.lbxy.service.impl.OrderServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author lmy
 * @description OrderCronTask order定时任务,为了防止一次性读入内存，分批读入
 * @date 2018/8/19
 */
public class OrderCronTask implements Runnable {

    private OrderService orderService = new OrderServiceImpl();

    @Override
    public void run() {
        Page<Order> orderPage = orderService.getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(1);
        while (!orderPage.isLastPage()) {
            orderPage.getList().forEach(order -> {
                Date availableDate = order.getAvailableDate();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime availableLocalDateTime = LocalDateTime.ofInstant(availableDate.toInstant(), zoneId);

                LocalDate availableLocalDate = availableLocalDateTime.toLocalDate();
                LocalTime availableLocalTime = availableLocalDateTime.toLocalTime();

                if (order.getStatus() == OrderStatus.UN_COMPLETED) {
                    if (availableLocalDate.isBefore(LocalDate.now())) {
                        // 已经过期
                        order.setStatus(OrderStatus.CANCELED);
                    } else if (availableLocalTime.getHour() == 0 && availableLocalTime.getMinute() == 0) {
                        //  未过期
                    } else if (availableLocalTime.plusHours(2).isBefore(LocalTime.now())) {
                        // 已经过期
                        order.setStatus(OrderStatus.CANCELED);
                    }
                } else if (order.getStatus() == OrderStatus.WAIT_COMPLETE) {
                    if (availableLocalDate.isBefore(LocalDate.now())) {
                        //todo 已经过期
                    } else if (availableLocalTime.getHour() == 0 && availableLocalTime.getMinute() == 0) {
                        //todo  未过期
                    } else if (availableLocalTime.plusHours(2).isBefore(LocalTime.now())) {
                        //todo 已经过期
                    }
                } else if (order.getStatus() == OrderStatus.COMPLETED) {
                    if (availableLocalDate.isBefore(LocalDate.now())) {
                        //todo 已经过期
                    } else if (availableLocalTime.getHour() == 0 && availableLocalTime.getMinute() == 0) {
                        //todo  未过期
                    } else if (availableLocalTime.plusHours(2).isBefore(LocalTime.now())) {
                        //todo 已经过期
                    }
                }

                order.update();
            });

            orderPage = orderService.getUnCompletedAndWaitCompletedAndCompletedOrdersByPage(orderPage.getPageNumber() + 1);
        }
    }
}
