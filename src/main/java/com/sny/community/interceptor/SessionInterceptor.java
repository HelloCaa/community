package com.sny.community.interceptor;

import com.sny.community.mapper.UserMapper;
import com.sny.community.model.User;
import com.sny.community.model.UserExample;
import com.sny.community.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Resource
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        HashMap<String, String> map = new HashMap<>();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie:cookies
            ) {
                if (cookie.getName().equals("token")) {
                    map.put("token", cookie.getValue());
                }
                if (cookie.getName().equals("accountId")){
                    map.put("accountId", cookie.getValue());
                }
                if (map.size() == 2){
                    break;
                }
            }
        }
        //判断用户客户端的cookie中的token是否已经在我们的数据库中，
        //并从我们的数据库将用户信息返回，以便前端进行用户信息的显示
        if (map.size() == 2){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andTokenEqualTo(map.get("token")).andAccountIdEqualTo(map.get("accountId"));
            List<User> users = userMapper.selectByExample(userExample);
            if(users.size() != 0){
                //将信息写到session作用域
                request.getSession().setAttribute("user", users.get(0));
                Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                request.getSession().setAttribute("unreadCount", unreadCount);
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
