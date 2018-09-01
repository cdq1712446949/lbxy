package com.lbxy.core.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author lmy
 * @description InterceptorUtil
 * @date 2018/8/31
 */
public class InterceptorUtil {

    public static boolean containsParameter(Method method, String parameterName) {
        boolean flag = false;
        for (Parameter parameter : method.getParameters()) {

            if (parameterName.equals(parameter.getName())) {
                flag = true;
            }
        }
        return flag;
    }

    public static int getParameterIndex(Method method, String parameterName) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameterName.equals(parameters[i].getName())) {
                return i;
            }
        }
        return 0;
    }
}
