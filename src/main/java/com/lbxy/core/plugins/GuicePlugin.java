package com.lbxy.core.plugins;

import com.google.inject.Guice;
import com.jfinal.plugin.IPlugin;
import com.lbxy.core.modues.DaoModule;
import com.lbxy.core.modues.ServiceModule;
import com.lbxy.core.plugins.cache.Injector;

/**
 * @author lmy
 * @description GuicePlugin
 * @date 2018/9/19
 */
public class GuicePlugin implements IPlugin {
    @Override
    public boolean start() {
        try {
            com.google.inject.Injector injector = Guice.createInjector(new DaoModule(), new ServiceModule());
            Injector.setInjector(injector);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
