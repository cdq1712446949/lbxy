package com.lbxy.event;

import net.dreamlu.event.core.ApplicationEvent;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description CreateBillEvent
 * @date 2018/9/18
 */
public class CreateBillEvent extends ApplicationEvent {
    private long orderId;
    private long userId;
    private BigDecimal money;

    public CreateBillEvent(Object source, long orderId, long userId, BigDecimal money, int status) {
        super(source);
        this.orderId = orderId;
        this.userId = userId;
        this.money = money;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public CreateBillEvent(Object source) {
        super(source);
    }
}
