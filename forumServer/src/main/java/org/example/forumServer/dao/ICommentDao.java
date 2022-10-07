package org.example.forumServer.dao;

import org.example.forumServer.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentDao {
    List<Comment> findAll();

    Comment findOneCommentById(Integer commentId);

    List<Comment> findCommentByCondition(Comment comment);

    Integer addComment(Comment comment);

    Integer updateComment(Comment comment);

    Integer updateCount(Comment comment);

    Integer delComment(Integer commentId);
}
