package com.lbxy.service.impl;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.lbxy.common.NotificationType;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.NotificationDao;
import com.lbxy.model.Notification;
import com.lbxy.service.NotificationService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lmy
 * @description NotificationServiceImpl
 * @date 2018/8/28
 */
@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

    @Resource
    private NotificationDao notificationDAO;

    @Override
    public Notification getActiveNotification() {
        return notificationDAO.getNotificationByActive(NotificationType.ACTIVE);
    }

    @Override
    public Page<Notification> getAllNotification(int pn) {
        return notificationDAO.getAllNotificationByPn(pn);
    }

    @Override
    @Before(Tx.class)
    public boolean notificationEdit(int id, String content) {
        Notification notification = new Notification();
        notification.set("id", id);
        notification.set("content", content);
        return notificationDAO.notificationUpdate(notification);
    }

    @Override
    @Before(Tx.class)
    public boolean notificationSave(String content, int active) {
        this.cancelActive();

        Notification notification = new Notification();
        notification.set("content", content);
        notification.set("createdDate", new Date());
        notification.set("active", active);
        return notificationDAO.notificationSave(notification);
    }

    @Override
    @Before(Tx.class)
    public boolean cancelActive(int id) {
        Notification notification = new Notification();
        notification.set("id", id);
        notification.set("active", NotificationType.INACTIVE);
        return notificationDAO.notificationUpdate(notification);
    }

    @Override
    public Notification findNotificationByActive() {
        return notificationDAO.getNotificationByActive(NotificationType.ACTIVE);
    }

    @Override
    @Before(Tx.class)
    public boolean notificationUpdate(Notification notification) {
        this.cancelActive();
        return notificationDAO.notificationUpdate(notification);
    }

    private void cancelActive() {
        Notification activeNotification = this.findNotificationByActive();
        if (activeNotification != null) {
            activeNotification.setActive(NotificationType.INACTIVE);
            notificationDAO.notificationUpdate(activeNotification);
        }
    }
}
