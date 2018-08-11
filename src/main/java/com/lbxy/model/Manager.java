package com.lbxy.model;

import com.jfinal.plugin.activerecord.Model;

public class Manager extends Model<Manager> {
    public static final Manager dao=new Manager().dao();
}
