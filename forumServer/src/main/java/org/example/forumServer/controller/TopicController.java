package org.example.forumServer.controller;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.model.Topic;
import org.example.forumServer.service.ITopicService;
import org.example.forumServer.utils.JsonResult;
import org.example.forumServer.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topic")
public class TopicController {
    @Autowired
    private ITopicService topicService;

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping("/findall")
    public JsonResult findAll(Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<Topic> topics = topicService.findAll(pageNum, pageSize);
        result.addDatas("topicListWithPage", topics);
        return result;
    }

    @RequestMapping("/findallbyhot")
    public JsonResult findAllByHot(Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<Topic> topics = topicService.findAllByHot(pageNum, pageSize);
        result.addDatas("topicListWithPage", topics);
        return result;
    }

    @RequestMapping("/findonebycondition")
    public JsonResult findTopicByCondition(@RequestBody Topic topic, Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<Topic> topics = topicService.findTopicByCondition(topic, pageNum, pageSize);
        result.addDatas("topicListWithPage", topics);
        return result;
    }

    @RequestMapping("/findone")
    public JsonResult findOneTopicById(Integer topicId) {
        JsonResult result = new JsonResult();
        Topic topic = topicService.findOneTopicById(topicId);
        if (topic != null) {
            result.setSuccess();
            result.addDatas("topicInfo", topic);
        } else {
            result.setFail("????????????????????????");
        }
        return result;
    }

    @RequestMapping("/deltopic")
    public JsonResult delTopic(Integer topicId) {
        JsonResult result = new JsonResult();
        if (topicService.delTopic(topicId)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/updatetopic")
    public JsonResult updateTopic(@RequestBody Topic topic) {
        JsonResult result = new JsonResult();
        if (topicService.updateTopic(topic)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/addtopic")
    public JsonResult addTopic(@RequestBody Topic topic, @RequestHeader(value = "token") String token) {
        JsonResult result = new JsonResult();
        topic.setUserId(tokenUtil.getCacheUser(token).getUserId());
        if (topicService.addTopic(topic)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }
}
