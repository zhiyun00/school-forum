package org.example.forumServer.service.impl;

import org.example.forumServer.constant.NotificationCst;
import org.example.forumServer.dao.IThumbDao;
import org.example.forumServer.model.Comment;
import org.example.forumServer.model.Notification;
import org.example.forumServer.model.Thumb;
import org.example.forumServer.model.Topic;
import org.example.forumServer.service.ICommentService;
import org.example.forumServer.service.INotificationService;
import org.example.forumServer.service.IThumbService;
import org.example.forumServer.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ThumbServiceImpl implements IThumbService {
    @Autowired
    IThumbDao thumbDao;

    @Autowired
    ITopicService topicService;

    @Autowired
    ICommentService commentService;

    @Autowired
    INotificationService notificationService;

    @Override
    public Thumb findThumbById(Integer thumbId) {
        return thumbDao.findThumbById(thumbId);
    }

    @Override
    public List<Thumb> findThumbByCondition(Thumb thumb) {
        return thumbDao.findThumbByCondition(thumb);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addThumb(Thumb thumb) {
        if (null != thumb.getCommentId()) {
            Comment comment = commentService.findOneCommentById(thumb.getCommentId());
            comment.setThumbCount(comment.getThumbCount() + 1); //赞加1
            commentService.updateCount(comment);
            //todo 赞了评论添加通知
        } else if (null != thumb.getTopicId()) {
            Topic topic = topicService.findOneTopicById(thumb.getTopicId());
            topic.setViewCount(topic.getViewCount() - 1); //因为在查询一个时增加了一次浏览次数，所以在此减去
            topic.setThumbCount(topic.getThumbCount() + 1); //赞加1
            topicService.updateCount(topic);
            //todo 赞了帖子添加通知
            Notification notification = new Notification(topic.getUserId(), thumb.getFromUserId(), NotificationCst.THUMB_TOPIC, topic.getTopicId());
            notificationService.addNotification(notification);
        }
        return thumbDao.addThumb(thumb) > 0 ? true : false;
    }

    @Override
    public Integer delThumb(Integer thumbId) {
        return thumbDao.delThumb(thumbId);
    }
}
