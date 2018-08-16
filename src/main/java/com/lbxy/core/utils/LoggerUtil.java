package com.lbxy.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 96428 on 2017/7/21.
 * This in ssmjavaconfig, samson.common.utils
 */
public class LoggerUtil {
    /**
     * 是否开启Debug
     */
    private static boolean isDebug = LoggerFactory.getLogger(LoggerUtil.class).isDebugEnabled();

    public static void info(Class<?> clazz , String message){
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.info(message);
    }
    /**
     * Debug 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     */
    public static void debug(Class<?> clazz , String message){
        if(!isDebug) return;
        Logger logger = LoggerFactory.getLogger(clazz);
        logger.debug(message);
    }
    /**
     * Debug 输出
     * @param clazz  	目标.Class
     * @param fmtString 输出信息key
     * @param value		输出信息value
     */
    public static void fmtDebug(Class<?> clazz, String fmtString, Object...value){
        if(!isDebug) return;
        if(StringUtils.isEmpty(fmtString)) return;

        if(null != value && value.length != 0)
            fmtString = String.format(fmtString, value);

        debug(clazz, fmtString);
    }
    /**
     * Error 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     * @param e			异常类
     */
    public static void error(Class<?> clazz , String message, Exception e){
        Logger logger = LoggerFactory.getLogger(clazz);

        if(null == e){
            logger.error(message);
            return;
        }

        logger.error(message, e);
    }
    /**
     * Error 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     */
    public static void error(Class<?> clazz , String message){
        error(clazz, message, null);
    }
    /**
     * 异常填充值输出
     * @param clazz 	目标.Class
     * @param fmtString	输出信息key
     * @param e			异常类
     * @param value		输出信息value
     */
    public static void fmtError(Class<?> clazz, Exception e, String fmtString, Object...value){
        if(StringUtils.isEmpty(fmtString)) return;

        if(null != value && value.length != 0)
            fmtString = String.format(fmtString, value);

        error(clazz, fmtString, e);
    }
    /**
     * 异常填充值输出
     * @param clazz		目标.Class
     * @param fmtString 输出信息key
     * @param value		输出信息value
     */
    public static void fmtError(Class<?> clazz, String fmtString, Object...value) {

        if(StringUtils.isEmpty(fmtString)) return;

        if(null != value && value.length != 0)
            fmtString = String.format(fmtString, value);

        error(clazz, fmtString);
    }
}
