package com.sny.community.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = -7815139695614681121L;
    Integer parentId;
    String content;
    Integer type;
}
