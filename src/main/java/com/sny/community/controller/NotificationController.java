package com.sny.community.controller;

import com.sny.community.dto.NotificationDTO;
import com.sny.community.enums.NotificationTypeEnum;
import com.sny.community.model.User;
import com.sny.community.service.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Resource
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);

        if(NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()){
            return "redirect:/question/"+notificationDTO.getOuterid();
        }else {
            return "redirect:/";
        }
    }
}
