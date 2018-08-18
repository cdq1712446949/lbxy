package com.lbxy.common;

public class Status {
    public static int DELETED = 1;//该记录被删除
    public static int NORMAL = 0;
    public static  int ACCEPTED=2;//表示订单被接单但是并未完成
    public static int COMPLETED=3;//表示订单完成状态
    public static int PAY=4;//表示金额支出
    public static int INCOME=5;//表示金额收入
    public static  int MANAGER=6;//表示权相为管理员
    public static int BLACK=7;//表示用户被拉黑
    public static  int NOANTHENTICATION=8;//表示用户待审核
    public static  int THROUGH=9;//用户已经通过审核
}
