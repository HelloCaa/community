package com.sny.community.controller;

import com.sny.community.dto.QuestionDTO;
import com.sny.community.mapper.QuestionMapper;
import com.sny.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class QuestionController {

    @Resource
    QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model){
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("question", questionDTO);
        return "question";
    }
}
