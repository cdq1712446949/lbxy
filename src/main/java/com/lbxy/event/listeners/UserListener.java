package com.lbxy.event.listeners;

import com.lbxy.core.plugins.cache.Injector;
import com.lbxy.event.UpdateUserBalanceEvent;
import com.lbxy.service.UserService;
import net.dreamlu.event.core.EventListener;

/**
 * @author lmy
 * @description UserListener
 * @date 2018/9/17
 */
public class UserListener {

    private final UserService userService = Injector.getInjector().getInstance(UserService.class);

    @EventListener(async = true)
    public void listenUpdateUserBalanceEvent(UpdateUserBalanceEvent event) {
        userService.updateUserBalance(event.getUserId(), event.getReward());
    }
}
