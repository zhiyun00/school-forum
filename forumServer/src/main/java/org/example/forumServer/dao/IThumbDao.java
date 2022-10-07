package org.example.forumServer.dao;

import org.example.forumServer.model.Thumb;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IThumbDao {

    Thumb findThumbById(Integer thumbId);

    List<Thumb> findThumbByCondition(Thumb thumb);

    Integer addThumb(Thumb thumb);

    Integer delThumb(Integer thumbId);
}
