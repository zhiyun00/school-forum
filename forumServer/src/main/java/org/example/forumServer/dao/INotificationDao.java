package org.example.forumServer.dao;

import org.example.forumServer.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationDao {
    List<Notification> findAll();

    Notification findOneNotificationById(Integer notificationId);

    List<Notification> findNotificationByCondition(Notification notification);

    Integer addNotification(Notification notification);

    Integer handleNotification(Integer toUserId);
}
