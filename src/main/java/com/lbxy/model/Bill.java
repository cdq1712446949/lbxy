package com.lbxy.model;

import com.jfinal.plugin.activerecord.Model;

public class Bill extends Model<Bill> {
    public static final Bill dao=new Bill().dao();
}
