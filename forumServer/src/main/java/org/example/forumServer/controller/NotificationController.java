package org.example.forumServer.controller;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.model.Notification;
import org.example.forumServer.service.INotificationService;
import org.example.forumServer.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    INotificationService notificationService;

    @RequestMapping("/findby")
    public JsonResult findNotificationByCondition(@RequestBody Notification notification, Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<Notification> notifications = notificationService.findNotificationByCondition(notification, pageNum, pageSize);
        result.addDatas("notificationListWithPage", notifications);
        return result;
    }

    @RequestMapping("/handle")
    public JsonResult handleNotification(Integer toUserId) {
        JsonResult result = new JsonResult();
        if (notificationService.handleNotification(toUserId)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }
}
