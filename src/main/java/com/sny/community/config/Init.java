package com.sny.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author sny
 * @CreateTime 2022-07-11  19:44
 * @Description TODO
 * @Version 1.0
 */
@Configuration
public class Init {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Bean
    public AliyunConfig getAliyunConfig() {
        AliyunConfig aliyunConfig = new AliyunConfig();

        aliyunConfig.setEndPoint(redisTemplate.opsForValue().get("endPoint"));
        aliyunConfig.setAccessKeyId(redisTemplate.opsForValue().get("accessKeyId"));
        aliyunConfig.setAccessKeySecret(redisTemplate.opsForValue().get("accessKeySecret"));
        aliyunConfig.setBucketName(redisTemplate.opsForValue().get("bucketName"));
        aliyunConfig.setPath(redisTemplate.opsForValue().get("path"));
        return aliyunConfig;
    }
}
