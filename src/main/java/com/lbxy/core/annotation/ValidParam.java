package com.lbxy.core.annotation;

import java.lang.annotation.*;

/**
 * @author lmy
 * @description ValidParam
 * @date 2018/8/16
 */
/*
    TYPE,// 类、接口、注解类型或枚举
    FIELD, //属性
    METHOD, //方法
    PARAMETER,// 用于描述参数
    CONSTRUCTOR,//构造方法
    LOCAL_VARIABLE,//局部变量
    ANNOTATION_TYPE,//注解类
    PACKAGE //包
 */
@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.PARAMETER)//目标是方法参数
@Documented//文档生成时，该注解将被包含在javadoc中，可去掉
public @interface ValidParam {

    /**
     * 注解分组
     *
     * @return
     */
    Class<?>[] groups() default {};
}