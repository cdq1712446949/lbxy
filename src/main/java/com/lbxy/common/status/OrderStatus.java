package com.lbxy.common.status;

/**
 * @author lmy
 * @description OrderStatus
 * @date 2018/8/18
 */
public class OrderStatus {
    //status
    public static final int UN_PAYED = 0;//表示订单已发布但是未支付
    public static final int UN_COMPLETED = 1;//表示订单已经支付，未被接单
    public static final int WAIT_COMPLETE = 2;//表示订单被接单但是并未完成
    public static final int COMPLETED = 3;//表示订单完成状态，处于待结算状态
    public static final int SETTLED = 4;//表示订单已结算
    public static final int CANCELED = 5; //取消的订单

    //canPayStatus
    public static final int CAN_PAY_BACK = 1; //设置WAIT_COMPLETE状态下可以申请退款
    public static final int CANT_PAY_BACK = 0;
}
