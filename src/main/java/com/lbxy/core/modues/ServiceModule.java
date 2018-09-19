package com.lbxy.core.modues;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.lbxy.service.*;
import com.lbxy.service.impl.*;

/**
 * @author lmy
 * @description DaoModule
 * @date 2018/9/19
 */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BillService.class).to(BillServiceImpl.class).in(Singleton.class);
        bind(FleaService.class).to(FleaServiceImpl.class).in(Singleton.class);
        bind(FormService.class).to(FormServiceImpl.class).in(Singleton.class);
        bind(ImageService.class).to(ImageServiceImpl.class).in(Singleton.class);
        bind(LostFoundService.class).to(LostFoundServiceImpl.class).in(Singleton.class);
        bind(ManagerService.class).to(ManagerServiceImpl.class).in(Singleton.class);
        bind(NotificationService.class).to(NotificationServiceImpl.class).in(Singleton.class);
        bind(OrderService.class).to(OrderServiceImpl.class).in(Singleton.class);
        bind(PayBackService.class).to(PayBackServiceImpl.class).in(Singleton.class);
        bind(TreeHoleService.class).to(TreeHoleServiceImpl.class).in(Singleton.class);
        bind(UserService.class).to(UserServiceImpl.class).in(Singleton.class);
    }
}
