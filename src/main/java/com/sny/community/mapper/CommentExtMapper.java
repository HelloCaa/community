package com.sny.community.mapper;

import com.sny.community.model.Comment;
import com.sny.community.model.CommentExample;
import com.sny.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}