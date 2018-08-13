package com.lbxy.controller;

import com.alibaba.fastjson.JSONObject;
import com.lbxy.common.response.MessageVo;
import com.lbxy.common.response.ResponseStatus;
import com.lbxy.model.Bill;
import com.lbxy.service.BillService;
import com.lbxy.service.impl.BillServiceImpl;

import java.util.Date;
import java.util.List;

public class BillController extends BaseController {

    private BillService billService;

    public BillController() {
        billService = new BillServiceImpl();
    }

    public void index() {
        List<Bill> list = billService.getBill(getJsonParamToInt("userId"));
        renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("获取成功").setData(list));
    }

    public void addBill() {
        JSONObject param = new JSONObject();
        param = getJsonParam();
        Bill bill = new Bill();
        bill.set("orderId", param.getIntValue("orderId"));
        bill.set("userId", param.getIntValue("userId"));
        bill.set("money", param.getIntValue("money"));
        bill.set("status", param.getIntValue("status"));
        bill.set("createdDate", new Date());
        if (billService.add(bill)) {
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("账单记录已生成"));
        } else {
            renderJson(new MessageVo().setStatus(ResponseStatus.success).setMessage("账单记录生成失败"));
        }
//        int orderid=param.getIntValue("orderId");
//        int  userId=param.getIntValue("userId");
//        int money=param.getIntValue("money");
//        int status=param.getIntValue("status");

//        renderJson(String.valueOf(orderid)+String.valueOf(userId)+String.valueOf(money)+String.valueOf(status));
    }


}
