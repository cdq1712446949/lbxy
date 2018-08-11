package com.lbxy.model;

import com.jfinal.plugin.activerecord.Model;

public class Order extends Model<Order> {
    public static final Order dao = new Order().dao();
}
