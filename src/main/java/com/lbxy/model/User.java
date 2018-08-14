package com.lbxy.model;

import com.jfinal.plugin.activerecord.Model;

public class User extends Model<User> {
    public static final User dao = new User().dao();
    private static final long serialVersionUID = -1129210998618815724L;
}
