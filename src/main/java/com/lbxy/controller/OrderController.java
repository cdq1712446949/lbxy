package com.lbxy.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.request.CreateOrderBean;
import com.lbxy.common.response.MessageVo;
import com.lbxy.common.response.MessageVoUtil;
import com.lbxy.common.response.ResponseStatus;
import com.lbxy.core.annotation.ValidParam;
import com.lbxy.core.interceptors.CheckLoginInterceptor;
import com.lbxy.model.Order;
import com.lbxy.service.OrderService;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.sql.ResultSet;

@Before(CheckLoginInterceptor.class)
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    public void index(int pn) {
        Page<Order> page = orderService.getOrdersByPage(pn);
        renderJson(MessageVoUtil.success(page));
    }

    public void getOrderById(int orderId) {
        renderJson(MessageVoUtil.success("请求成功", orderService.findById(orderId)));
    }

    public void createOrder(int userId, @ValidParam @Para("") CreateOrderBean orderInfo) {
        boolean i = orderService.createOrder(userId, orderInfo);
        if (i) {
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("发单成功"));
        } else {
            renderJson(new MessageVo().setStatus(ResponseStatus.error).setMessage("发单失败"));
        }
    }

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

    public void ownerPostOrders(int pn, int userId) {
        Page<Order> result = orderService.getOwnerPostOrders(pn, userId);
        renderJson(MessageVoUtil.success("请求成功", result));
    }

    public void ownerAcceptOrders(int pn, int userId) {
        Page<Order> result = orderService.getOwnerAcceptOrders(pn, userId);
        renderJson(MessageVoUtil.success("请求成功", result));
    }

    public void cancelOrder(int orderId) {
        int result = orderService.cancelOrder(orderId);
        if (result != 0) {
            renderJson(MessageVoUtil.success("订单取消成功"));
        } else {
            renderJson(MessageVoUtil.error("订单取消失败"));
        }
    }

    public void settleOrder(int orderId) throws Exception {
        int result = orderService.settleOrder(orderId);
        if (result != 0) {
            renderJson(MessageVoUtil.success("订单取消成功"));
        } else {
            renderJson(MessageVoUtil.error("订单取消失败"));
        }
    }

    public void completeOrder(int orderId) {
        boolean result = orderService.complete(orderId);
        if (result) {
            renderJson(MessageVoUtil.success("订单完成"));
        } else {
            renderJson(MessageVoUtil.error("订单完成失败"));
        }
    }

    public void deleteOrder(int orderId) {
        boolean result = orderService.delete(orderId);
        if (result) {
            renderJson(MessageVoUtil.success("删除成功"));
        } else {
            renderJson(MessageVoUtil.success("删除失败"));
        }
    }

}
