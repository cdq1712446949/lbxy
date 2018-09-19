package com.lbxy.core;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.cron4j.Cron4jPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.lbxy.controller.*;
import com.lbxy.core.handlers.ServletExcludeHandler;
import com.lbxy.core.interceptors.InjectionInterceptor;
import com.lbxy.core.interceptors.ParamValidateInterceptor;
import com.lbxy.core.interceptors.exception.ExceptionsInterceptor;
import com.lbxy.core.plugins.AnnotationInjectionPlugin;
import com.lbxy.core.plugins.GuicePlugin;
import com.lbxy.model._MappingKit;
import net.dreamlu.event.EventPlugin;

/**
 * API引导式配置
 */
public class Config extends JFinalConfig {

    /**
     * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
     * <p>
     * 使用本方法启动过第一次以后，会在开发工具的 debug、run config 中自动生成
     * 一条启动配置，可对该自动生成的配置再添加额外的配置项，例如 VM argument 可配置为：
     * -XX:PermSize=64M -XX:MaxPermSize=256M
     */
    public static void main(String[] args) {
        /**
         * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
         */
        JFinal.start("src/main/webapp", 80, "/");
    }

    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("database.jdbcUrl"), PropKit.get("database.username"), PropKit.get("database.password"));
    }

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        PropKit.use("globalConfig.properties");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        me.setJsonFactory(new FastJsonFactory());
        me.setJsonDatePattern(PropKit.get("jFinalConfig.jsonDatePattern"));
//        me.setBaseUploadPath(PropKit.get("jFinalConfig.uploadPath"));
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        me.add("/order", OrderController.class);
        me.add("/bill", BillController.class);
        me.add("/back", ManagerController.class);
        me.add("/user", UserController.class);
        me.add("/upload", ImageController.class);
        me.add("/flea", FleaController.class);
        me.add("/treehole", TreeholeController.class);
        me.add("/lostfound", LostfoundController.class);
        me.add("/notification", NotificationController.class);
        me.add("/form", FormController.class);
    }

    public void configEngine(Engine me) {
        me.setDevMode(PropKit.getBoolean("devMode", false));
    }

    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置 druid 数据库连接池插件
        DruidPlugin druidPlugin = Config.createDruidPlugin();
        me.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        _MappingKit.mapping(arp);
        me.add(arp);

        /*
        缓存
         */
        me.add(new EhCachePlugin());

        /*
        基于注解 依赖注入
         */
//        me.add(new AnnotationInjectionPlugin("com.lbxy.service.impl", "com.lbxy.dao","com.lbxy.manager"));

        /*
        配置定时任务
         */
        Cron4jPlugin cp = new Cron4jPlugin(PropKit.get("cron4j.configFile"));
        me.add(cp);

        /*
        事件驱动
         */
        EventPlugin eventPlugin = new EventPlugin();
        eventPlugin.async();
        eventPlugin.scanPackage("com.lbxy.event");
        me.add(eventPlugin);


        /*
        guice
         */
        me.add(new GuicePlugin());
    }

    /**
     * 配置全局拦截器
     */
    public void configInterceptor(Interceptors me) {
        me.addGlobalActionInterceptor(new ExceptionsInterceptor()); //全局异常拦截
        me.addGlobalActionInterceptor(new ParamValidateInterceptor());  // 使用hibernate-validator参数校验
//        me.addGlobalActionInterceptor(new InjectionInterceptor()); //控制器拦截注入
    }

    /**
     * 配置处理器
     */
    public void configHandler(Handlers me) {
        me.add(new ServletExcludeHandler()); // 过滤servlet请求
    }
}
