package org.example.forumServer.dao;

import org.example.forumServer.model.Advise;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAdviseDao {
    List<Advise> findAll();

    List<Advise> findAllMessage();

    List<Advise> findAdviseByCategory(Integer category);

    Integer addAdvise(Advise advise);

    Integer handleAdvise(Advise advise);

    Integer deleteAdvise(Integer adviseId);

}
