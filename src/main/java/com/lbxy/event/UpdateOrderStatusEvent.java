package com.lbxy.event;

import net.dreamlu.event.core.ApplicationEvent;

/**
 * @author lmy
 * @description UpdateOrderStatusEvent
 * @date 2018/9/18
 */
public class UpdateOrderStatusEvent extends ApplicationEvent {
    private static final long serialVersionUID = -7781443869483947239L;

    private long orderId;

    private int status;

    public UpdateOrderStatusEvent(Object source, long orderId, int status) {
        super(source);
        this.orderId = orderId;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public UpdateOrderStatusEvent(Object source) {
        super(source);
    }
}
