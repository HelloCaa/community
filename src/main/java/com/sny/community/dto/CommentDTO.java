package com.sny.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    Integer parentId;
    String content;
    Integer type;
}
