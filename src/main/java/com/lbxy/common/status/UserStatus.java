package com.lbxy.common.status;

/**
 * @author lmy
 * @description UserStatus
 * @date 2018/8/18
 */
public class UserStatus {
    public static final int UNAUTHENTICATION = 0;  //未认证
    public static final int WAIT_AUTHENTICATION = 2;  //等待认证
    public static final int AUTHENTACATED = 1;  //通过认证
    public static final int BLOCKED = -1;  //黑名单
}
