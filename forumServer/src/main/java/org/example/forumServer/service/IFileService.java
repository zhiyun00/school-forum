package org.example.forumServer.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String saveUploadFile(MultipartFile file);
}
