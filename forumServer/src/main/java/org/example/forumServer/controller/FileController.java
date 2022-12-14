package org.example.forumServer.controller;

import org.example.forumServer.service.IFileService;
import org.example.forumServer.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping({"/file"})
public class FileController {
    @Autowired
    IFileService fileService;

    @RequestMapping({"/upload"})
    public JsonResult fileUpload(@RequestParam("file") MultipartFile file) {
        JsonResult jsonResult = new JsonResult();
        if (file.isEmpty()) {
            jsonResult.setFail("文件为空！");
        }
        String filePath = fileService.saveUploadFile(file);
        if (null != filePath) {
            jsonResult.setSuccess("上传成功！");
            jsonResult.addDatas("filePath", filePath);
        } else {
            jsonResult.setFail("上传失败！");
        }
        return jsonResult;
    }

    @RequestMapping({"/test"})
    public void testUpload(String filename) throws FileNotFoundException {
        InputStream in = new FileInputStream(new File("D:/var/upload" + filename));// 将该文件加入到输入流之中
    }
}
