package com.lbxy;

import com.lbxy.service.impl.BillServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author lmy
 * @description UtilTest
 * @date 2018/8/14
 */

class UtilTest {
    @Test
    void test() throws URISyntaxException, ClassNotFoundException {

        System.out.println(UtilTest.class.getResource("/"));;

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
}
