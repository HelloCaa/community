package com.sny.community.service;

import com.sny.community.exception.CustomizeErrorCode;
import com.sny.community.exception.CustomizeException;
import com.sny.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getCommentor() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

    }
}
