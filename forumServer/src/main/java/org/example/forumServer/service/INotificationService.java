package org.example.forumServer.service;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.model.Notification;

import java.util.List;

public interface INotificationService {
    List<Notification> findAll();

    Notification findOneNotificationById(Integer notificationId);

    PageInfo<Notification> findNotificationByCondition(Notification notification, Integer pageNum, Integer pageSize);

    Boolean addNotification(Notification notification);

    Boolean handleNotification(Integer toUserId);
}
