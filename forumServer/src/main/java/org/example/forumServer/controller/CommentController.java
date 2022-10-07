package org.example.forumServer.controller;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.model.Comment;
import org.example.forumServer.service.ICommentService;
import org.example.forumServer.utils.JsonResult;
import org.example.forumServer.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    ICommentService commentService;

    @Autowired
    TokenUtil tokenUtil;

    @RequestMapping("/findall")
    public JsonResult findAll(Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<Comment> comments = commentService.findAll(pageNum, pageSize);
        result.addDatas("commentListWithPage", comments);
        return result;
    }

    @RequestMapping("/findone")
    public JsonResult findOneCommentById(Integer commentId) {
        JsonResult result = new JsonResult();
        Comment comment = commentService.findOneCommentById(commentId);
        if (comment != null) {
            result.setSuccess();
            result.addDatas("commentInfo", comment);
        } else {
            result.setFail("未找到此条评论！");
        }
        return result;
    }

    @RequestMapping("/delcomment")
    public JsonResult delComment(Integer commentId) {
        JsonResult result = new JsonResult();
        if (commentService.delComment(commentId)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/updatecomment")
    public JsonResult updateComment(@RequestBody Comment comment) {
        JsonResult result = new JsonResult();
        if (commentService.updateComment(comment)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/addcomment")
    public JsonResult addComment(@RequestBody Comment comment, @RequestHeader(value = "token") String token) {
        JsonResult result = new JsonResult();
        comment.setUserId(tokenUtil.getCacheUser(token).getUserId());
        if (commentService.addComment(comment)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/findbycondition")
    public JsonResult findCommentByCondition(@RequestBody Comment comment, Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<Comment> comments = commentService.findCommentByCondition(comment, pageNum, pageSize);
        result.addDatas("commentListWithPage", comments);
        return result;
    }
}
