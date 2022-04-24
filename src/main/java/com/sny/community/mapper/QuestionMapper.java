package com.sny.community.mapper;

import com.sny.community.dto.QuestionDTO;
import com.sny.community.dto.QuestionQueryDTO;
import com.sny.community.model.Question;
import com.sny.community.model.QuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface QuestionMapper {
    long countByExample(QuestionExample example);

    int deleteByExample(QuestionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Question row);

    int insertSelective(Question row);

    List<Question> selectByExampleWithBLOBsWithRowbounds(QuestionExample example, RowBounds rowBounds);

    List<Question> selectByExampleWithBLOBs(QuestionExample example);

    List<Question> selectByExampleWithRowbounds(QuestionExample example, RowBounds rowBounds);

    List<Question> selectByExample(QuestionExample example);

    Question selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") Question row, @Param("example") QuestionExample example);

    int updateByExampleWithBLOBs(@Param("row") Question row, @Param("example") QuestionExample example);

    int updateByExample(@Param("row") Question row, @Param("example") QuestionExample example);

    int updateByPrimaryKeySelective(Question row);

    int updateByPrimaryKeyWithBLOBs(Question row);

    int updateByPrimaryKey(Question row);
}