package com.sny.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    @RequestMapping("/test")
    public void test(HttpServletRequest request){
        System.out.println(System.getProperty("user.dir"));
        System.out.println(request.getContextPath());
    }
}
