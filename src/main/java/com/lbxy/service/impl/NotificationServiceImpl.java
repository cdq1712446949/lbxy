package com.lbxy.service.impl;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.common.NotificationType;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.NotificationDAO;
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
    private NotificationDAO notificationDAO;

    @Override
    public Notification getActiveNotification() {
        return notificationDAO.getNotificationByActive(NotificationType.ACTIVE);
    }

    @Override
    public Page<Notification> getAllNotification(int pn) {
        return notificationDAO.getAllNotificationByPn(pn);
    }

    @Override
    public boolean notificationEdit(int id,String content) {
        Notification notification=new Notification();
        notification.set("id" ,id);
        notification.set("content",content);
        return notificationDAO.notificationUpData(notification);
    }

    @Override
    public boolean notificationSave(String content,int active) {
        Notification notification=new Notification();
        notification.set("content",content);
        notification.set("createdDate",new Date());
        notification.set("active",active);
        return notificationDAO.notificationSave(notification);
    }

    @Override
    public boolean cancelActive(int id) {
        Notification notification=new Notification();
        notification.set("id",id);
        notification.set("active",NotificationType.INACTIVE);
        return notificationDAO.notificationUpData(notification);
    }

    @Override
    public Notification findNotificationByActive() {
        return notificationDAO.getNotificationByActive(NotificationType.ACTIVE);
    }

    @Override
    public boolean notificationUpData(Notification notification) {
        return notificationDAO.notificationUpData(notification);
    }


}
