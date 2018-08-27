package com.lbxy.service.impl;

import com.lbxy.common.NotificationType;
import com.lbxy.core.annotation.Service;
import com.lbxy.dao.NotificationDAO;
import com.lbxy.model.Notification;
import com.lbxy.service.NotificationService;

import javax.annotation.Resource;

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

}
