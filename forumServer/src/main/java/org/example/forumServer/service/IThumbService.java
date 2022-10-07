package org.example.forumServer.service;

import org.example.forumServer.model.Thumb;

import java.util.List;

public interface IThumbService {

    Thumb findThumbById(Integer thumbId);

    List<Thumb> findThumbByCondition(Thumb thumb);

    Boolean addThumb(Thumb thumb);

    Integer delThumb(Integer thumbId);
}
