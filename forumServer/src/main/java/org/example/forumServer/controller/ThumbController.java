package org.example.forumServer.controller;

import org.example.forumServer.model.Thumb;
import org.example.forumServer.service.IThumbService;
import org.example.forumServer.utils.JsonResult;
import org.example.forumServer.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/thumb")
public class ThumbController {
    @Autowired
    IThumbService thumbService;

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping("/findone")
    public Thumb findThumbById(Integer thumbId) {
        return thumbService.findThumbById(thumbId);
    }

    @RequestMapping("/findby")
    public List<Thumb> findThumbByCondition(Thumb thumb) {
        return thumbService.findThumbByCondition(thumb);
    }

    @RequestMapping("/add")
    public JsonResult addThumb(@RequestBody Thumb thumb, @RequestHeader(value = "token") String token) {
        thumb.setFromUserId(tokenUtil.getCacheUser(token).getUserId());
        JsonResult result = new JsonResult();
        if (thumbService.addThumb(thumb)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/del")
    public Integer delThumb(Integer thumbId) {
        return thumbService.delThumb(thumbId);
    }
}
