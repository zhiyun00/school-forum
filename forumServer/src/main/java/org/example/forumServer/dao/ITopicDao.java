package org.example.forumServer.dao;

import org.example.forumServer.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITopicDao {

    List<Topic> findAll();

    List<Topic> findAllByHot();

    Topic findOneTopicById(Integer topicId);

    List<Topic> findTopicByCondition(Topic topic);

    Integer addTopic(Topic topic);

    Integer updateTopic(Topic topic);

    Integer updateCount(Topic topic);

    Integer delTopic(Integer topicId);
}
