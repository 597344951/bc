package com.zltel.broadcast.question.dao;

import com.zltel.broadcast.question.bean.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(Integer questionId);

    int insert(Question question);

    Question get(int questionId);

    List<Question> query(@Param("orgId") int orgId,
                         @Param("keyword") String keyword,
                         @Param("type") List<Integer> type,
                         @Param("subject") List<Integer> subject,
                         @Param("category") List<Integer> category,
                         @Param("rowStart") int rowStart,
                         @Param("pageSize") int pageSize);

    long count(@Param("orgId") int orgId,
               @Param("keyword") String keyword,
               @Param("type") List<Integer> type,
               @Param("subject") List<Integer> subject,
               @Param("category") List<Integer> category);

    List<Question> rand(@Param("orgId") int orgId,
                        @Param("keyword") String keyword,
                        @Param("type") List<Integer> type,
                        @Param("subject") List<Integer> subject,
                        @Param("category") List<Integer> category,
                        @Param("size") int size);
}