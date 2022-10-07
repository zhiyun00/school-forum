package org.example.forumServer.dao;

import org.example.forumServer.model.Image;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IImageDao {
    List<Image> findAll();

    Image findOneImageById(Integer imageId);

    List<Image> findImageByCondition(Image image);

    Integer addImage(Image image);

    Integer delImage(Integer imageId);
}
