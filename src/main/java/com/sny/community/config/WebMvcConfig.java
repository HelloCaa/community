package com.sny.community.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.locations}")
    private String locations;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        /*解释:
        /files/**: 相当于 别名的意思
        file:D:/图片/: 本地文件的路径*/
        registry.addResourceHandler("/files/**")
                .addResourceLocations(locations);
        // "file:/Users/shengningyi/IdeaProjects/community/src/main/resources/static/img/"
        // WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
