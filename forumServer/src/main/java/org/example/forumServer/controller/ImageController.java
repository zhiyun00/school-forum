package org.example.forumServer.controller;

import org.example.forumServer.model.Image;
import org.example.forumServer.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    IImageService imageService;

    @RequestMapping("/findall")
    public List<Image> findAll() {
        return imageService.findAll();
    }

    @RequestMapping("/findone")
    public Image findOneImageById(Integer imageId) {
        return imageService.findOneImageById(imageId);
    }

    @RequestMapping("/findby")
    public List<Image> findImageByCondition(Image image) {
        return imageService.findImageByCondition(image);
    }

    @RequestMapping("/add")
    public Integer addImage(Image image) {
        return imageService.addImage(image);
    }

    @RequestMapping("/del")
    public Integer delImage(Integer imageId) {
        return imageService.delImage(imageId);
    }
}
