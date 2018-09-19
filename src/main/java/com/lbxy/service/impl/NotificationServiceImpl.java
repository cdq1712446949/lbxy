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
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author lmy
 * @description NotificationServiceImpl
 * @date 2018/8/28
 */
public class NotificationServiceImpl implements NotificationService {

    private final NotificationDao notificationDao;

    @Inject
    public NotificationServiceImpl(NotificationDao notificationDao) {
        this.notificationDao = notificationDao;
    }

    @Override
    public List<Notification> getActiveNotification() {
        return notificationDao.getNotificationByActive(NotificationType.ACTIVE);
    }

    @Override
    public Page<Notification> getAllNotification(int pn) {
        return notificationDao.getAllNotificationByPn(pn);
    }

    @Override
    @Before(Tx.class)
    public boolean notificationEdit(int id,String title, String content) {
        Notification notification = new Notification();
        notification.set("id", id);
        notification.set("content", content);
        notification.set("title",title);
        return notificationDao.notificationUpdate(notification);
    }

    @Override
    @Before(Tx.class)
    public boolean notificationSave(String title,String content, int active) {
        Notification notification = new Notification();
        notification.set("title",title);
        notification.set("content", content);
        notification.set("createdDate", new Date());
        notification.set("active", active);
        return notificationDao.notificationSave(notification);
    }

    @Override
    @Before(Tx.class)
    public boolean cancelActive(int id) {
        Notification notification = new Notification();
        notification.set("id", id);
        notification.set("active", NotificationType.INACTIVE);
        return notificationDao.notificationUpdate(notification);
    }

    @Override
    @Before(Tx.class)
    public boolean notificationUpdate(Notification notification) {
        return notificationDao.notificationUpdate(notification);
    }
}
