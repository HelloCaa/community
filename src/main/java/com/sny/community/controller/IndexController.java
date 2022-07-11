package com.sny.community.controller;

import com.sny.community.cache.HotTagCache;
import com.sny.community.dto.PaginationDTO;
import com.sny.community.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@Slf4j
public class IndexController {

    @Resource
    QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tag){

        //根据客户端传过来的当前页码和页面大小获取数据
        if ("".equals(search)){
            search = null;
        }
        if ("".equals(tag)){
            tag = null;
        }
        log.info("hhhhhhahahahahahahh");
        PaginationDTO pagination = questionService.list(search, tag, page, size);
        List<String> tags = HotTagCache.getTopN();
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", tags);
        return "index";
    }
}
