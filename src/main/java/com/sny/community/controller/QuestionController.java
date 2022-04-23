package com.sny.community.controller;

import com.sny.community.dto.CommentDTO;
import com.sny.community.dto.QuestionDTO;
import com.sny.community.dto.ResultDTO;
import com.sny.community.enums.CommentTypeEnum;
import com.sny.community.service.CommentService;
import com.sny.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class QuestionController {

    @Resource
    QuestionService questionService;

    @Resource
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

    @GetMapping("/increaseLikeCount/{id}")
    @ResponseBody
    public ResultDTO increaseLikeCount(@PathVariable(name = "id") Integer id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultDTO<Integer> resultDTO = new ResultDTO<>();
        Object user = request.getSession().getAttribute("user");
        if (user == null){
            resultDTO.setCode(-1);
            resultDTO.setMessage("请先登录！");
            return resultDTO;
        }
        resultDTO.setData(commentService.increaseLikeCount(id));
        resultDTO.setCode(200);
        return resultDTO;
    }
}
