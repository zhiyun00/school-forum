package org.example.forumServer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.forumServer.constant.NotificationCst;
import org.example.forumServer.dao.ICommentDao;
import org.example.forumServer.model.Comment;
import org.example.forumServer.model.Notification;
import org.example.forumServer.model.Topic;
import org.example.forumServer.model.User;
import org.example.forumServer.service.ICommentService;
import org.example.forumServer.service.INotificationService;
import org.example.forumServer.service.ITopicService;
import org.example.forumServer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    ICommentDao commentDao;

    @Autowired
    ITopicService topicService;

    @Autowired
    private IUserService userService;

    @Autowired
    INotificationService notificationService;

    @Override
    public PageInfo<Comment> findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentDao.findAll();
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }

    @Override
    public Comment findOneCommentById(Integer commentId) {
        return commentDao.findOneCommentById(commentId);
    }

    @Override
    public PageInfo<Comment> findCommentByCondition(Comment comment, Integer pageNum, Integer pageSize) {
        if (null != comment.getContent() && !"".equals(comment.getContent()))
            comment.setContent("%" + comment.getContent() + "%");
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> commentList = commentDao.findCommentByCondition(comment);
        PageInfo<Comment> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addComment(Comment comment) {
        //TopicId???RootId???????????????
        //??????????????????????????????????????????
        if (null != comment.getTopicId()) {
            Topic topic = topicService.findOneTopicById(comment.getTopicId());
            topic.setViewCount(topic.getViewCount() - 1); //????????????????????????????????????????????????????????????????????????
            topic.setCommentCount(topic.getCommentCount() + 1); //????????????1
            topicService.updateCount(topic);
            //????????????????????????
            Notification notification = new Notification(topic.getUserId(), comment.getUserId(), NotificationCst.COMMENT_TOPIC, topic.getTopicId());
//            notification.setToUserId(topic.getUserId());
//            notification.setFromUserId(comment.getUserId());
//            notification.setSubject(NotificationCst.COMMENT_TOPIC);
//            notification.setTopicId(topic.getTopicId());
            notificationService.addNotification(notification);
        }
        //??????????????????????????????????????????
        else if (null != comment.getRootId()) {
            Comment rootComment = this.findOneCommentById(comment.getRootId());
            rootComment.setCommentCount(rootComment.getCommentCount() + 1); //????????????1
            this.updateCount(rootComment);
            //todo ????????????????????????
        }
        //???????????????????????????1
        User user = userService.findOneUserById(comment.getUserId());
        user.setCommentCount(user.getCommentCount() + 1);
        userService.updateCount(user);
        return commentDao.addComment(comment) > 0 ? true : false;
    }

    @Override
    public Boolean updateComment(Comment comment) {
        return commentDao.updateComment(comment) > 0 ? true : false;
    }

    @Override
    public Boolean updateCount(Comment comment) {
        return commentDao.updateCount(comment) > 0 ? true : false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean delComment(Integer commentId) {
        Comment comment = this.findOneCommentById(commentId);
        //TopicId???RootId???????????????
        //????????????????????????????????????????????????
        if (null != comment.getTopicId()) {
            Topic topic = topicService.findOneTopicById(comment.getTopicId());
            topic.setViewCount(topic.getViewCount() - 1); //????????????????????????????????????????????????????????????????????????
            topic.setCommentCount(topic.getCommentCount() - 1); //????????????1
            topicService.updateCount(topic);
        }
        //???????????????????????????????????????????????????
        else if (null != comment.getRootId()) {
            Comment rootComment = this.findOneCommentById(comment.getRootId());
            rootComment.setCommentCount(rootComment.getCommentCount() - 1); //????????????1
            this.updateCount(rootComment);
        }
        //???????????????????????????1
        User user = userService.findOneUserById(comment.getUserId());
        user.setCommentCount(user.getCommentCount() - 1);
        userService.updateCount(user);
        return commentDao.delComment(commentId) > 0 ? true : false;
    }
}
