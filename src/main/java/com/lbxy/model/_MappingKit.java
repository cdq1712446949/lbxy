package com.lbxy.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("bill", "id", Bill.class);
		arp.addMapping("community", "id", Community.class);
		arp.addMapping("flea", "id", Flea.class);
		arp.addMapping("lostfound", "id", Lostfound.class);
		arp.addMapping("manager", "userName", Manager.class);
		arp.addMapping("notice", "id", Notice.class);
		arp.addMapping("order", "id", Order.class);
		arp.addMapping("treehole", "id", Treehole.class);
		arp.addMapping("user", "id", User.class);
	}
}

