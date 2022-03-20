package com.sny.community.controller;

import com.sny.community.dto.CommentCreateDTO;
import com.sny.community.dto.CommentDTO;
import com.sny.community.dto.QuestionDTO;
import com.sny.community.service.CommentService;
import com.sny.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
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

        List<CommentDTO> comments = commentService.listByQuestionId(id);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        return "question";
    }
}
