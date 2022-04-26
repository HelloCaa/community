package com.sny.community.controller;

import com.sny.community.dto.FileDTO;
import com.sny.community.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

@Controller
public class FileController {
    @Value("${uploadPath}")
    String uploadPath;
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest req){
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        String filePath = uploadPath;
        Calendar instance = Calendar.getInstance();
        String month = (instance.get(Calendar.MONTH) + 1)+"month/";
        User user = (User) req.getSession().getAttribute("user");
        String userInfo = user.getId() + "_" + user.getName() + "/";
        filePath = filePath + month + userInfo;
        String pathname = filePath + fileName;
        File dest = new File(pathname);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        String invented_address = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + "/files/" + month + userInfo + fileName;
        fileDTO.setUrl(invented_address);
        return fileDTO;
    }
}
