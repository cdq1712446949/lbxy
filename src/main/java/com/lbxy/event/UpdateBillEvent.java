package com.lbxy.event;

import net.dreamlu.event.core.ApplicationEvent;

/**
 * @author lmy
 * @description UpdateBillEvent
 * @date 2018/9/19
 */
public class UpdateBillEvent extends ApplicationEvent {
    private static final long serialVersionUID = 7121245747863582147L;

    private long orderId;
    private long userId;
    private int status;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public UpdateBillEvent(Class<?> source, long orderId, long userId, int status) {
        super(source);
        this.orderId = orderId;
        this.userId = userId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
