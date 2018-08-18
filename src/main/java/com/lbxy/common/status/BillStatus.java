package com.lbxy.common.status;

/**
 * @author lmy
 * @description BillStatus
 * @date 2018/8/18
 */
public class BillStatus {
    public static final int PAY = 0;  //支出
    public static final int INCOME = 1;  //收入
    public static final int WAIT_SETTLE = 2;  //等待结算，即钱在平台上，还未往送件人转
}
