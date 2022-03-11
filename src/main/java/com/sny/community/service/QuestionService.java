package com.sny.community.service;

import com.sny.community.dto.QuestionDTO;
import com.sny.community.mapper.QuestionMapper;
import com.sny.community.mapper.UserMapper;
import com.sny.community.model.Question;
import com.sny.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
//写一个Service层，用来将Dao层获得到的数据进行进一步整合和封装，以便在Controller层直接调用
public class QuestionService {

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    public List<QuestionDTO> list(){
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOS = new ArrayList<>();
        for (Question question:questions
             ) {
            //通过question表的creator字段与user表的id（主键自增）字段整合数据
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //Spring提供的一个方法，快速的将前者对象里面的属性拷贝到后者目标对象的属性中
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }

        return  questionDTOS;
    }
}