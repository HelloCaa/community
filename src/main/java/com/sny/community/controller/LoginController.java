package com.sny.community.controller;

import com.sny.community.dto.AccountDTO;
import com.sny.community.dto.ResultDTO;
import com.sny.community.model.User;
import com.sny.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {
    @Resource
    UserService userService;

    @RequestMapping("/toLogin")
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        if (user != null) {
            request.getRequestDispatcher("/").forward(request, response);
            return null;
        }
        return "login";
    }

    @RequestMapping("/toRegister")
    public String register(HttpServletRequest request, HttpServletResponse response) {
        return "register";
    }

    @RequestMapping("/judgeUserName/{userName}")
    @ResponseBody
    public ResultDTO<Boolean> judgeUserName(@PathVariable("userName") String userName) {
        ResultDTO<Boolean> resultDTO = new ResultDTO<>();
        if (userName == null || "".equals(userName) || userName.length() > 10 ) {
            resultDTO.setCode(400);
            resultDTO.setMessage("用户名不合法");
            resultDTO.setData(false);
        } else if (userService.queryUserByName(userName)) {
            resultDTO.setCode(200);
            resultDTO.setMessage("用户名合法");
            resultDTO.setData(true);
        } else {
            resultDTO.setCode(300);
            resultDTO.setMessage("用户名被占用了");
            resultDTO.setData(false);
        }
        return resultDTO;
    }

    @RequestMapping("/judgeLoginId/{loginId}")
    @ResponseBody
    public ResultDTO<Boolean> judgeLoginId(@PathVariable("loginId") String loginId) {
        ResultDTO<Boolean> resultDTO = new ResultDTO<>();
        if (loginId == null || loginId.length() != 9 || !StringUtils.isNumeric(loginId)) {
            resultDTO.setCode(400);
            resultDTO.setMessage("id不合法");
            resultDTO.setData(false);
        } else if (userService.queryUserById(loginId)) {
            resultDTO.setCode(200);
            resultDTO.setMessage("id合法");
            resultDTO.setData(true);
        } else {
            resultDTO.setCode(300);
            resultDTO.setMessage("id被占用了");
            resultDTO.setData(false);
        }
        return resultDTO;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultDTO<Boolean> register(@RequestBody AccountDTO accountDTO){
        ResultDTO<Boolean> resultDTO = new ResultDTO<>();
        if (accountDTO == null || accountDTO.getLoginId() == null || accountDTO.getPassword() == null){
            resultDTO.setCode(300);
            resultDTO.setMessage("注册失败");
            resultDTO.setData(false);
            return resultDTO;
        }
        User user = new User();
        user.setName(accountDTO.getUserName());
        user.setAccountId(accountDTO.getLoginId());
        user.setToken(accountDTO.getPassword());
        user.setAvatarUrl("https://elasticsearch.cn/static/common/avatar-max-img.png?rand=789");
        userService.createOrUpdate(user);
        resultDTO.setCode(200);
        resultDTO.setData(true);
        resultDTO.setMessage("注册成功");
        return resultDTO;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultDTO<Boolean> login(@RequestBody AccountDTO accountDTO, HttpServletResponse response){
        ResultDTO<Boolean> resultDTO = new ResultDTO<>();
        if (userService.queryUserByAccountIdAndPassword(accountDTO.getLoginId(), accountDTO.getPassword())){
            resultDTO.setData(true);
            response.addCookie(new Cookie("token", accountDTO.getPassword()));
            response.addCookie(new Cookie("accountId", accountDTO.getLoginId()));
        }
        return resultDTO;
    }
}
