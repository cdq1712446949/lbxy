package com.lbxy.event;

import net.dreamlu.event.core.ApplicationEvent;

import java.math.BigDecimal;

/**
 * @author lmy
 * @description UpdateUserBalanceEvent
 * @date 2018/9/17
 */
public class UpdateUserBalanceEvent extends ApplicationEvent {
    private long userId;
    private BigDecimal reward;
    public UpdateUserBalanceEvent(Object source) {
        super(source);
    }

    public long getUserId() {
        return userId;
    }

    public UpdateUserBalanceEvent setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public BigDecimal getReward() {
        return reward;
    }

    public UpdateUserBalanceEvent setReward(BigDecimal reward) {
        this.reward = reward;
        return this;
    }
}
