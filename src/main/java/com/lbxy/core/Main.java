package com.lbxy.core;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author lmy
 * @description Main
 * @date 2018/8/20
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources("com.lbxy.core".replace(".", "/"));
        while (enumeration.hasMoreElements()) {
            System.out.println(enumeration.nextElement().getPath());
        }
    }
}
