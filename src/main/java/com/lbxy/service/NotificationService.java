package com.lbxy.service;

import com.jfinal.plugin.activerecord.Page;
import com.lbxy.model.Notification;

import java.util.List;

/**
 * @author lmy
 * @description NotificationService
 * @date 2018/8/28
 */
public interface NotificationService {

    List<Notification> getActiveNotification();

    Page<Notification> getAllNotification(int pn);

    boolean notificationEdit(int id,String title,String content);

    boolean notificationSave(String title,String content,int active);

    boolean cancelActive(int id);

    boolean notificationUpdate(Notification notification);

}
