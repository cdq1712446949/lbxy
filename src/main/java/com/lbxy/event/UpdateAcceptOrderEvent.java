package com.lbxy.event;

import com.lbxy.model.User;
import net.dreamlu.event.core.ApplicationEvent;

/**
 * @author lmy
 * @description UpdateAcceptOrderEvent
 * @date 2018/9/18
 */
public class UpdateAcceptOrderEvent extends ApplicationEvent {

    private static final long serialVersionUID = -5181769878220763406L;
    private User user;
    private long orderId;

    public UpdateAcceptOrderEvent(Object source, User user, long orderId) {
        super(source);
        this.user = user;
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public UpdateAcceptOrderEvent(Object source) {
        super(source);
    }
}
