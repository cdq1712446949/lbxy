package com.lbxy.model;

import com.jfinal.plugin.activerecord.Model;

public class Notice extends Model<Notice> {
    public static final Notice dao = new Notice().dao();
}
