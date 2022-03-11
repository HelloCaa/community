package com.sny.community.dto;

import com.sny.community.model.User;
import lombok.Data;

@Data //lombok用来简化set和get方法
//用来将user和question信息结合，并在服务器和客户端之间传递的类
public class QuestionDTO {
    private int id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;
}
