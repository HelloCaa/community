package com.sny.community.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author sny
 * @CreateTime 2022-07-11  17:43
 * @Description TODO
 * @Version 1.0
 */
@Data
public class AliyunConfig {
    public String endPoint;
    public String accessKeyId;
    public String accessKeySecret;
    public String bucketName;
    public String path;
}
