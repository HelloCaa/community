package com.sny.community.interceptor;

import com.sny.community.mapper.UserMapper;
import com.sny.community.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

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
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
