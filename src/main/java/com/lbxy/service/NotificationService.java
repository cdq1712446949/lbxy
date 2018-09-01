package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Notification;

/**
 * @author lmy
 * @description NotificationService
 * @date 2018/8/28
 */
public interface NotificationService {

    Notification getActiveNotification();

    Page<Notification> getAllNotification(int pn);

    boolean notificationEdit(int id,String content);

    boolean notificationSave(String content,int active);

    boolean cancelActive(int id);

    Notification findNotificationByActive();

    boolean notificationUpData(Notification notification);

}
