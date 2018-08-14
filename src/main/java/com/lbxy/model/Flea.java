package com.lbxy.model;

import com.jfinal.plugin.activerecord.Model;

public class Flea extends Model<Flea> {
    public static final Flea dao = new Flea().dao();
}
