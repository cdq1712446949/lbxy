package com.lbxy.core.annotation;

import java.lang.annotation.*;

/**
 * @author lmy
 * @description Service
 * @date 2018/8/20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Service {
    String value() default "";

    boolean enableTransaction() default false;
}
