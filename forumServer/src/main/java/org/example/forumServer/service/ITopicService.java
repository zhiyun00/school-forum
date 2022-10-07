package org.example.forumServer.service;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.model.Topic;

public interface ITopicService {
    PageInfo<Topic> findAll(Integer pageNum, Integer pageSize);

    PageInfo<Topic> findAllByHot(Integer pageNum, Integer pageSize);

    Topic findOneTopicById(Integer topicId);

    PageInfo<Topic> findTopicByCondition(Topic topic, Integer pageNum, Integer pageSize);

    Boolean addTopic(Topic topic);

    Boolean updateTopic(Topic topic);

    Boolean updateCount(Topic topic);

    Boolean delTopic(Integer topicId);
}
