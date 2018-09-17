package com.lbxy.event.listeners;

import com.lbxy.core.plugins.cache.InjectionCache;
import com.lbxy.event.UpdateUserBalanceEvent;
import com.lbxy.service.UserService;
import net.dreamlu.event.core.EventListener;

/**
 * @author lmy
 * @description UserListener
 * @date 2018/9/17
 */
public class UserListener {

    private UserService userService = (UserService) InjectionCache.get("userService");

    @EventListener(async = true)
    public void listenUpdateUserBalanceEvent(UpdateUserBalanceEvent event) {
        userService.updateUserBalance(event.getUserId(), event.getReward());
    }
}
