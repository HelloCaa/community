package com.sny.community.controller;

import com.sny.community.dto.QuestionDTO;
import com.sny.community.mapper.QuestionMapper;
import com.sny.community.mapper.UserMapper;
import com.sny.community.model.Question;
import com.sny.community.model.User;
import com.sny.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    UserMapper userMapper;

    @Resource
    QuestionService questionService;

    @GetMapping("/")
    public String hello(HttpServletRequest request,
                        Model model){
        //获取用户客户端已有的cookies
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie:cookies
            ) {
                //判断有没有我们自己的token这个cookie
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    //判断用户客户端的cookie中的token是否已经在我们的数据库中，
                    //并从我们的数据库将用户信息返回，以便前端进行用户信息的显示
                    User user =  userMapper.findByToken(token);
                    if(user != null){
                        //将信息写到session作用域
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        return "index";
    }
}
