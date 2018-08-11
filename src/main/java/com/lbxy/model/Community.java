package com.lbxy.model;

import com.jfinal.plugin.activerecord.Model;

public class Community extends Model<Community> {
    public static final Community dao = new Community().dao();
}
