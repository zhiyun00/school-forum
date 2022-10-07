package org.example.forumServer.service;

import org.example.forumServer.model.Advise;

import java.util.List;

public interface IAdviseService {
    List<Advise> findAll();

    List<Advise> findAllMessage();

    List<Advise> findAdviseByCategory(Integer category);

    Boolean addAdvise(Advise advise);

    Boolean handleAdvise(Advise advise);

    Boolean deleteAdvise(Integer adviseId);
}
