package com.lbxy;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.JFinalJsonFactory;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.lbxy.controller.BillController;
import com.lbxy.controller.CommunityController;
import com.lbxy.controller.ManagerController;
import com.lbxy.controller.OrderController;
import com.lbxy.model.*;

/**

 * API引导式配置
 */
public class Config extends JFinalConfig {
	
	/**
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 * 
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
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		me.setDevMode(true);
        me.setJsonFactory(new JFinalJsonFactory());
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
        me.add("/order", OrderController.class);
        me.add("/community",CommunityController.class);
        me.add("/bill",BillController.class);
        me.add("/back",ManagerController.class);
//        me.add()
    }
	
	public void configEngine(Engine me) {
	    me.setDevMode(true);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin("jdbc:mysql://127.0.0.1:3306/lbxy?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull", "root", "980814");
		me.add(druidPlugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        me.add(arp);
        arp.addMapping("Community", Community.class);
        arp.addMapping("Order", Order.class);
        arp.addMapping("User",User.class);
        arp.addMapping("Bill",Bill.class);
        arp.addMapping("Manager",Manager.class);
        arp.addMapping("TreeHole",TreeHole.class);
        arp.addMapping("Flea",Flea.class);
        arp.addMapping("LostFound",LostFound.class);

        Engine engine = arp.getEngine();

        // 上面的代码获取到了用于 sql 管理功能的 Engine 对象，接着就可以开始配置了
        engine.setSourceFactory(new ClassPathSourceFactory());
        engine.addSharedMethod(new StrKit());

        me.add(arp);

	}
	
	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		
	}
}
