package org.example.forumServer.service;

import org.example.forumServer.model.Image;

import java.util.List;

public interface IImageService {
    List<Image> findAll();

    Image findOneImageById(Integer imageId);

    List<Image> findImageByCondition(Image image);

    Integer addImage(Image image);

    Integer delImage(Integer imageId);
}
