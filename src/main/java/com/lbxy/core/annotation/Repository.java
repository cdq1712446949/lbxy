package com.lbxy.core.annotation;

import java.lang.annotation.*;

/**
 * @author lmy
 * @description Repository
 * @date 2018/8/20
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Repository {
    String value() default "";
}
