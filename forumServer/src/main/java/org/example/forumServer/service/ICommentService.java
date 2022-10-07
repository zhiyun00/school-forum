package org.example.forumServer.service;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.model.Comment;

public interface ICommentService {
    PageInfo<Comment> findAll(Integer pageNum, Integer pageSize);

    Comment findOneCommentById(Integer commentId);

    PageInfo<Comment> findCommentByCondition(Comment comment, Integer pageNum, Integer pageSize);

    Boolean addComment(Comment comment);

    Boolean updateComment(Comment comment);

    Boolean updateCount(Comment comment);

    Boolean delComment(Integer commentId);
}
