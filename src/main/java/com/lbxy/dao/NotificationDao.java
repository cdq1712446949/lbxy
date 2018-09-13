package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.NotificationType;
import com.lbxy.core.annotation.Repository;
import com.lbxy.model.Notification;

/**
 * @author lmy
 * @description NotificationDao
 * @date 2018/8/28
 */
@Repository
public class NotificationDao {

    public Notification getNotificationByActive(int type) {
       return Notification.dao.findFirst("select * from notification where active = ?", type);
    }

    public Page<Notification> getAllNotificationByPn(int pn){
        return Notification.dao.paginate(pn,10,"select *","from notification");
    }

    public boolean notificationUpdate(Notification notification){
        return notification.update();
    }

    public boolean notificationSave(Notification notification){
        return notification.save();
    }


}
