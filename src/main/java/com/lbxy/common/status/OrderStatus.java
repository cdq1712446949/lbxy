package com.lbxy.common.status;

/**
 * @author lmy
 * @description OrderStatus
 * @date 2018/8/18
 */
public class OrderStatus {
    public static final int UN_COMPLETED = 0;//表示订单未被接单
    public static final int WAIT_COMPLETE = 1;//表示订单被接单但是并未完成
    public static final int COMPLETED = 2;//表示订单完成状态
    public static final int SETTLED = 3;//表示订单已结算
}
