package com.lbxy.core.plugins.cache;


/**
 * @author lmy
 * @description Injector
 * @date 2018/9/19
 */
public class Injector {
    private static com.google.inject.Injector injector = null;

    public static com.google.inject.Injector getInjector() {
        return injector;
    }

    public static void setInjector(com.google.inject.Injector injector) {
        Injector.injector = injector;
    }
}
