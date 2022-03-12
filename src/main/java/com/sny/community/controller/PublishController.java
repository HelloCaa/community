package com.sny.community.controller;

import com.sny.community.mapper.QuestionMapper;
import com.sny.community.model.Question;
import com.sny.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//用来处理用户发布问题的Controller
@Controller
public class PublishController {

    @Resource
    QuestionMapper questionMapper;

    //通过不同的请求方式来返回不同的视图
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){

        //将用户在页面上输入的数据放到request里面，以便出错后回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        //判断用在页面输入数据的有效性
        if(title == null || title == ""){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error", "tag不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error", "用户未登陆");
            return "publish";
        }

        //将用户发布的问题封装到Question类对象中，并存到数据库
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
