package org.example.forumServer.controller;

import org.example.forumServer.model.Advise;
import org.example.forumServer.service.IAdviseService;
import org.example.forumServer.utils.JsonResult;
import org.example.forumServer.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/advise")
public class AdviseController {
    @Autowired
    private IAdviseService adviseService;

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping("/findall")
    public JsonResult findAll() {
        JsonResult result = new JsonResult();
        result.setSuccess();
        List<Advise> adviseList = adviseService.findAll();
        result.addDatas("adviseList", adviseList);
        return result;
    }

    @RequestMapping("/findallmessage")
    public JsonResult findAllMessage() {
        JsonResult result = new JsonResult();
        result.setSuccess();
        List<Advise> messageList = adviseService.findAllMessage();
        result.addDatas("messageList", messageList);
        return result;
    }

    @RequestMapping("/findbycondition")
    public JsonResult findAdviseByCategory(Integer category) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        List<Advise> adviseList = adviseService.findAdviseByCategory(category);
        result.addDatas("adviseList", adviseList);
        return result;
    }

    //举报
    @RequestMapping("/add")
    public JsonResult addAdvise(@RequestBody Advise advise, @RequestHeader(value = "token") String token) {
        JsonResult result = new JsonResult();
        advise.setAdviseUserId(tokenUtil.getCacheUser(token).getUserId());
        advise.setCategory(2);
        if (adviseService.addAdvise(advise)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    //添加留言
    @RequestMapping("/addmessage")
    public JsonResult addMessage(String content, @RequestHeader(value = "token") String token) {
        JsonResult result = new JsonResult();
        Advise advise = new Advise();
        advise.setContent(content);
        advise.setAdviseUserId(tokenUtil.getCacheUser(token).getUserId());
        advise.setCategory(1);
        if (adviseService.addAdvise(advise)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    //处理
    @RequestMapping("/handle")
    public JsonResult handleAdvise(Integer adviseId, @RequestHeader(value = "token") String token) {
        JsonResult result = new JsonResult();
        Advise advise = new Advise();
        advise.setAdviseId(adviseId);
        advise.setAdminId(tokenUtil.getCacheUser(token).getUserId());
        if (adviseService.handleAdvise(advise)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    //删除
    @RequestMapping("/delete")
    public JsonResult deleteAdvise(Integer adviseId) {
        JsonResult result = new JsonResult();
        if (adviseService.deleteAdvise(adviseId)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }
}
