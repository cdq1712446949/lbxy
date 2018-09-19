package com.lbxy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.lbxy.core.Config;
import com.lbxy.core.utils.NetWorkUtil;
import com.lbxy.core.utils.PasswordUtil;
import com.lbxy.model.Order;
import com.lbxy.model.User;
import com.lbxy.service.OrderService;
import com.lbxy.service.impl.OrderServiceImpl;
import com.lbxy.weixin.utils.PayCacheUtil;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * @author lmy
 * @description UtilTest
 * @date 2018/8/14
 */

class UtilTest {

    @BeforeAll
    static void before() {
        PropKit.use("globalConfig.properties");
    }

    @Test
    void test() throws URISyntaxException, ClassNotFoundException {

//        System.out.println(UtilTest.class.getResource("/"));;

//        Class clazz = BillServiceImpl.class;
//        ClassLoader loader = clazz.getClassLoader();
//        // 1. 通过classloader载入包路径，得到url
//        URL url = loader.getResource("com.lbxy.service.impl.BillServiceImpl".replace(".", "/"));
//        URI uri = url.toURI();
//        // 2. 通过File获得uri下的所有文件
//        File file = new File(uri);
//        File[] files = file.listFiles();
//        for (File f : files) {
//            String fName = f.getName();
//            if (!fName.endsWith(".class")) {
//                continue;
//            }
//            fName = fName.substring(0, fName.length() - 6);
//            String perfix = "o1.o1_a.";
//            String allName = perfix + fName;
//            // 3. 通过反射加载类
//            clazz = Class.forName(allName);
//            System.out.println(clazz);
//        }

    }

    @Test
    void test2() {
        new EhCachePlugin().start();
        PayCacheUtil.put("a", "b");
        System.out.println(PayCacheUtil.get("a", "1"));
    }

    @Test
    void test3() {
        System.out.println(PasswordUtil.createHash("123456"));
    }


    @Test
    void test5() throws InterruptedException {
        CacheManager ehCacheManager = new CacheManager();
        ehCacheManager.addCache("qq");
        for (String a :
                ehCacheManager.getCacheNames()) {
            System.out.println(a);
        }
        Ehcache cache = ehCacheManager.getCache("qq");
        Element e = new Element("aa", "aa", 1, 1);
        cache.put(e);
        System.out.println(cache.get("aa").getObjectValue().toString());

        Thread.sleep(1000);

        System.out.println(cache.get("aa"));
    }

    @Test
    void test6() throws InterruptedException {
        System.out.println(new Date().getTime());
        int a = Math.toIntExact(new Date().getTime() / 1000);
        System.out.println(a);
    }

    @Test
    void test7() {
        JSONObject object = NetWorkUtil.uploadFile("https://sm.ms/api/upload2SM", "E:\\workPlace\\JavaWorkPlace\\javaWebWorkPlace\\lbxy\\src\\main\\webapp\\upload2SM\\IMG_0339.JPG", "smfile");
        System.out.println(object.toJSONString());
    }
}
