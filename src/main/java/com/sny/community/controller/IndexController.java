package com.sny.community.controller;

import com.sny.community.dto.PaginationDTO;
import com.sny.community.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Resource
    QuestionService questionService;

    Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search){

        //根据客户端传过来的当前页码和页面大小获取数据
        if ("".equals(search)){
            search = null;
        }
        logger.info("---------------用户访问了---------------");
        PaginationDTO pagination = questionService.list(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        return "index";
    }
}
