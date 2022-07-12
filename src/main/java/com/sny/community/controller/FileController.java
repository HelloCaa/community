package com.sny.community.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import com.sny.community.config.AliyunConfig;
import com.sny.community.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
@Slf4j
public class FileController {
    @Value("${uploadPath}")
    String uploadPath;

    @Resource
    AliyunConfig aliyunConfig;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(@RequestParam(value = "editormd-image-file", required = true) MultipartFile file, HttpServletRequest req) {
        String url = null;
        try {
            // 创建OSS实例
            OSSClient ossClient = new OSSClient(aliyunConfig.getEndPoint(), aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret());
            // 获取上传的文件的输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();
            String encode = URLEncoder.encode(fileName);

            // 在文件名称里面添加随机唯一值，使用UUID生成  把uuid生成里的-去掉
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uuid + fileName;
            // 拼接fileName  PATH是oss文件夹
            fileName = aliyunConfig.getPath() + fileName;

            /**
             * 调用oss方法实现上传
             * 第一个参数 Bucket名称
             * 第二个参数 上传oss文件路径和名称 aa/bb/1.jpg
             * 第三个参数 上传文件的输入流
             */

            //ossClient.putObject(BUCKETNAME, fileName, inputStream);   用这种方式putObject上传，拼接的url是下载！！！
            //ossClient.shutdown();

            PutObjectRequest putObjectRequest = new PutObjectRequest(aliyunConfig.getBucketName(), fileName, inputStream);
            ossClient.putObject(putObjectRequest);

//            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKETNAME, fileName, inputStream);
//            ossClient.putObject(putObjectRequest);
            // 关闭ossClient
            ossClient.shutdown();
            // 把上传到oss的路径返回
            // 需要将路径手动拼接出来，https://xxxxxx.oss-cn-shanghai.aliyuncs.com/edu/avatar/girl.jpg
            url = "https://" + aliyunConfig.getBucketName() + "." + aliyunConfig.getEndPoint().substring(7) + "/" + aliyunConfig.getPath() + uuid + encode;
        } catch (Exception e) {
            log.error("fileController:", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            return fileDTO;
        }
        FileDTO fileDTO = new FileDTO();
        fileDTO.setSuccess(1);
        fileDTO.setUrl(url);
        return fileDTO;
    }
}
