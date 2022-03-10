package com.sny.community.controller;

import com.sny.community.mapper.UserMapper;
import com.sny.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Resource
    UserMapper userMapper;

    @GetMapping("/")
    public String hello(HttpServletRequest request){
        //获取用户客户端已有的cookies
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
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
        return "index";
    }
}
