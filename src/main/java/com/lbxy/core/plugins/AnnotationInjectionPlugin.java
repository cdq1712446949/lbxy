package com.lbxy.core.plugins;

import com.jfinal.aop.Enhancer;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.core.annotation.Repository;
import com.lbxy.core.annotation.Service;
import com.lbxy.core.plugins.cache.InjectionCache;
import com.lbxy.core.utils.LoggerUtil;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lmy
 * @description AnnotationInjectionPlugin
 * @date 2018/8/21
 */
public class AnnotationInjectionPlugin implements IPlugin {

    private String basePackages[];

    public AnnotationInjectionPlugin(String... basePackage) {
        this.basePackages = basePackage;
    }

    private void searchClasses(Set<String> classes, File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                searchClasses(classes, f);
            }
        } else {
            String fileName = file.toURI().getPath();
            if (fileName.endsWith(".class")) {
                classes.add(fileName);
            }
        }
    }

    private void initializeClass(String classpath, Set<String> classes) {
        LoggerUtil.info(getClass(), "开始实例化注解类,实例化基本包名为：" + classpath);

        //service实例化
        for (String clazz : classes) {

            String serviceClassPackage = clazz.replace(classpath, "").replace('/','.').replace(".class","");
            Class serviceClass;
            try {
                serviceClass = Thread.currentThread().getContextClassLoader().loadClass(serviceClassPackage);

            } catch (ClassNotFoundException e) {
                LoggerUtil.error(getClass(), "未找到class：" + serviceClassPackage + " 程序结束", e);
                return;
            }

            Service serviceAnnotation = (Service) serviceClass.getAnnotation(Service.class);
            Repository repositoryAnnotation = (Repository) serviceClass.getAnnotation(Repository.class);

            String annotatedClassName = null;
            Object annotatedClassObject = null;
            if (serviceAnnotation != null) {
                //如果是在dao上的注解

                if (StrKit.notBlank(serviceAnnotation.value())) {
                    annotatedClassName = serviceAnnotation.value();
                } else {
                    annotatedClassName = StrKit.firstCharToLowerCase(serviceClass.getSimpleName());
                }

                if (serviceAnnotation.enableTransaction()) {
                    annotatedClassObject = Enhancer.enhance(serviceClass, Tx.class);
                } else {
                    try {
                        annotatedClassObject = serviceClass.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        LoggerUtil.error(getClass(), annotatedClassName + "实例化失败，程序退出", e);
                    }
                }
            } else if (repositoryAnnotation != null) {
                //如果是在dao上的注解

                if (StrKit.notBlank(repositoryAnnotation.value())) {
                    annotatedClassName = repositoryAnnotation.value();
                } else {
                    annotatedClassName = StrKit.firstCharToLowerCase(serviceClass.getSimpleName());
                }

                try {
                    annotatedClassObject = serviceClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    LoggerUtil.error(getClass(), annotatedClassName + "实例化失败，程序退出", e);
                }
            } else {
                // 如果扫描到的类没有Service或者repository注解，则跳过这个类
                continue;
            }

            InjectionCache.put(annotatedClassName, annotatedClassObject);
        }
    }


    private void injectEachOther(String classpath, Set<String> classes) {
        //service相互注入
        LoggerUtil.info(getClass(), "开始注解类之间的注入，注入基础包名：" + classpath);
        classes.forEach((clazz) -> {
            String serviceClassPackage = clazz.replace(classpath, "").replace('/','.').replace(".class","");
            Class serviceClass;
            try {
                serviceClass = Thread.currentThread().getContextClassLoader().loadClass(serviceClassPackage);

            } catch (ClassNotFoundException e) {
                LoggerUtil.error(getClass(), "未找到class：" + serviceClassPackage + " 程序结束", e);
                return;
            }

            //获取当前要注入field的service类
            Service service = (Service) serviceClass.getAnnotation(Service.class);
            Repository repository = (Repository) serviceClass.getAnnotation(Repository.class);
            String serviceName;
            if (service != null) {
                if (StrKit.notBlank(service.value())) {
                    serviceName = service.value();
                } else {
                    serviceName = StrKit.firstCharToLowerCase(serviceClass.getSimpleName());
                }
            } else if (repository != null) {
                if (StrKit.notBlank(repository.value())) {
                    serviceName = repository.value();
                } else {
                    serviceName = StrKit.firstCharToLowerCase(serviceClass.getSimpleName());
                }
            } else {
                //如果扫描到的类没有注解， 不执行注入
                return;
            }

            Object currentServiceClass = InjectionCache.get(serviceName);

            Field[] fields = serviceClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);  //允许改变该field而不管其可访问性
                Class memberType = field.getType(); //获取成员变量的类型
                if ((memberType.isAnnotationPresent(Service.class) || memberType.isAnnotationPresent(Repository.class)) && field.isAnnotationPresent(Resource.class)) {
                    //如果field的类型上有Service注解，即当前的field类型是service类. 并且当前field上有resource注解， 那么执行注入

                    Object targetObject = InjectionCache.get(field.getName());
                    try {
                        field.set(currentServiceClass, targetObject);
                    } catch (IllegalAccessException e) {
                        LoggerUtil.error(getClass(), "被注解的类之间相互注入出错，出错类：" + serviceName + "; 出错属性：" + field.getName(), e);
                    }
                }
            }
        });
    }

    /**
     * 将Service注入到controller
     * 由于jfinal的实现机制，不能在启动的时候进行controller层面的注入，controller层面的注入应该放在interceptor层面
     */
    private void injectToController() {


    }

    /**
     * 将dao注入到service
     * 此功能已经在injectEachOther中实现
     */
    private void injectToService() {

    }

    @Override
    public boolean start() {
        LoggerUtil.info(getClass(), "开始扫描注解");
        Set<String> classes = new HashSet<>();

        for (String basePackage : basePackages) {
            //获取当前包路径
            String classpath = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
            this.searchClasses(classes, new File(classpath + basePackage.replace('.', '/')));

            this.initializeClass(classpath, classes);
        }

        for (String basePackage : basePackages) {
            // 在全部实例化之后，在进行注入
            String classpath = Thread.currentThread().getContextClassLoader().getResource(".").getPath();
            this.injectEachOther(classpath, classes);
        }

        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
