package com.lbxy.dao;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.NotificationType;
import com.lbxy.common.PageConst;
import com.lbxy.common.status.CommonStatus;
import com.lbxy.model.Notification;

import java.util.List;

/**
 * @author lmy
 * @description NotificationDao
 * @date 2018/8/28
 */
public class NotificationDao {
    public NotificationDao() {
    }

    public List<Notification> getNotificationByActive(int type) {
        return Notification.dao.find("select * from notification where active = ? and status = ?", type, CommonStatus.NORMAL);
    }

    public Page<Notification> getAllNotificationByPn(int pn) {
        return Notification.dao.paginate(pn, PageConst.PAGE_SIZE, "select *", "from notification where active=?", NotificationType.ACTIVE);
    }

    public boolean notificationUpdate(Notification notification) {
        return notification.update();
    }

    public boolean notificationSave(Notification notification) {
        return notification.save();
    }


}
