package com.lbxy.event.listeners;

import com.lbxy.core.plugins.cache.InjectionCache;
import com.lbxy.event.UpdateAcceptOrderEvent;
import com.lbxy.event.UpdateOrderStatusEvent;
import com.lbxy.model.Order;
import com.lbxy.service.OrderService;
import net.dreamlu.event.core.EventListener;

/**
 * @author lmy
 * @description OrderListener
 * @date 2018/9/18
 */
public class OrderListener {

    private OrderService orderService = (OrderService) InjectionCache.get("orderService");


    @EventListener
    public void listenUpdateAcceptOrder(UpdateAcceptOrderEvent event) throws Exception {
        orderService.updateAcceptOrder(event.getOrderId(), event.getUser());
    }

    @EventListener(async = true)
    public void listenerUpdateOrderStatus(UpdateOrderStatusEvent event) {
        Order order = new Order();

        order.setId(event.getOrderId());
        order.setStatus(event.getStatus());

        orderService.updateModel(order);
    }
}
