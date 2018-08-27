package com.lbxy.dao;

import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Notification;

/**
 * @author lmy
 * @description NotificationDAO
 * @date 2018/8/28
 */
@Repository
public class NotificationDAO {
    public Notification getNotificationByActive(int type) {
       return Notification.dao.findFirst("select * from notification where active = ?", type);
    }
}
