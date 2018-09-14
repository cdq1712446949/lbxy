package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jfinal.plugin.ehcache.CacheName;
import com.jfinal.plugin.ehcache.EvictInterceptor;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.core.interceptors.WeixinLoginInterceptor;
import com.lbxy.model.Order;
import com.lbxy.service.OrderService;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

@Before(WeixinLoginInterceptor.class)
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    @Before({GET.class, CacheInterceptor.class})
    @CacheName("order")
    public void index(int pn) {
        Page<Record> page = orderService.getOrdersByPage(pn);
        renderJson(MessageVoUtil.success(page));
    }

    @Before(GET.class)
    public void getOrderById(int orderId) {
        renderJson(MessageVoUtil.success("请求成功", orderService.findById(orderId)));
    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("order")
    public void createOrder(long userId, @ValidParam @Para("") CreateOrderBean orderInfo) {
        long orderId = orderService.createOrder(userId, orderInfo);
        renderJson(MessageVoUtil.success("发单成功", orderId));
    }

    //不需要此方法，防止用户直接请求此action
//    @Before(POST.class)
//    public void payOrder(int orderId) {
//        boolean result = orderService.payOrder(orderId);
//        if (result) {
//            renderJson(MessageVoUtil.success("支付成功"));
//        } else {
//            renderJson(MessageVoUtil.error("支付失败，请联系管理员查看" + orderId + "订单"));
//        }
//    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("order")
    public void acceptOrder(int userId, @NotBlank int orderId) {
        int result = orderService.accept(orderId, userId);
        if (result == OrderService.SUCCESS) {
            renderJson(MessageVoUtil.success("接单成功"));
        } else if (result == OrderService.ERROR_USERID) {
            renderJson(MessageVoUtil.error("登陆过期，请重新登陆"));
        } else if (result == OrderService.NEED_MORE_INFO) {
            renderJson(MessageVoUtil.error("请完善个人信息"));
        } else if (result == OrderService.ORDER_NOT_EXIST) {
            renderJson(MessageVoUtil.error("订单不存在,请重试或联系管理员"));
        } else if (result == OrderService.CANT_ACCEPT_OWN_ORDER) {
            renderJson(MessageVoUtil.error("不能接受自己发布的订单！"));
        }
        {
            renderJson(MessageVoUtil.error("未知错误"));
        }
    }

    @Before(GET.class)
    public void ownerPostOrders(int pn, int userId) {
        Page<Order> result = orderService.getOwnerPostOrders(pn, userId);
        renderJson(MessageVoUtil.success("请求成功", result));
    }

    @Before(GET.class)
    public void ownerAcceptOrders(int pn, int userId) {
        Page<Order> result = orderService.getOwnerAcceptOrders(pn, userId);
        renderJson(MessageVoUtil.success("请求成功", result));
    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("order")
    public void cancelOrder(int orderId) {
        int result = orderService.cancelOrder(orderId);
        if (result != 0) {
            renderJson(MessageVoUtil.success("订单取消成功"));
        } else {
            renderJson(MessageVoUtil.error("订单取消失败"));
        }
    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("order")
    public void settleOrder(int orderId) throws Exception {
        boolean result = orderService.settleOrder(orderId);
        if (result) {
            renderJson(MessageVoUtil.success("订单取消成功"));
        } else {
            renderJson(MessageVoUtil.error("订单取消失败"));
        }
    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("order")
    public void completeOrder(int orderId) {
        boolean result = orderService.complete(orderId);
        if (result) {
            renderJson(MessageVoUtil.success("订单完成"));
        } else {
            renderJson(MessageVoUtil.error("订单完成失败"));
        }
    }

    @Before({POST.class, EvictInterceptor.class})
    @CacheName("order")
    public void deleteOrder(int orderId) {
        boolean result = orderService.delete(orderId);
        if (result) {
            renderJson(MessageVoUtil.success("删除成功"));
        } else {
            renderJson(MessageVoUtil.success("删除失败"));
        }
    }

}
