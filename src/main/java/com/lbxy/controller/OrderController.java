package com.lbxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.response.MessageVo;
import com.lbxy.common.response.ResponseStatus;
import com.lbxy.model.Order;
import com.lbxy.service.OrderService;
import com.lbxy.service.impl.OrderServiceImpl;

import java.util.Date;

public class OrderController extends BaseController {

    private OrderService orderService;

    public OrderController() {
        orderService = new OrderServiceImpl();
    }

    public void index() {
        JSONObject param = getJsonParam();
        int userId = param.getIntValue("userId");
        int pn = param.getIntValue("pn");
        Page<Order> page = orderService.getUserOrder(userId, pn);
        renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("获取成功").setData(page));
    }

    public void createdOrder() {
        Order order = new Order();
        getJsonParam(order);
        order.set("createdDate", new Date());
        boolean i = order.save();
        if (i) {
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("发单成功"));
        } else {
            renderJson(new MessageVo().setStatus(ResponseStatus.error).setMessage("发单失败"));
        }
    }

    public void acceptOrder() {
        JSONObject param = new JSONObject();
        param = getJsonParam();
        int id = ((JSONObject) param).getIntValue("id");
        int userId = ((JSONObject) param).getIntValue("acceptUserId");
        String acceptUserPhoneNumber = ((JSONObject) param).getString("acceptUserPhoneNumber");
        orderService.accept(id,userId,acceptUserPhoneNumber);
        renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("接单成功"));
    }

    public void completeOrder(){
        int id=getJsonParamToInt("id");
        orderService.complete(id);
        renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("订单完成"));
    }

    public void deleteOrder(){
        int id=getJsonParamToInt("id");
        orderService.delete(id);
        renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("删除成功"));
    }

}
