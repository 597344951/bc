package com.zltel.broadcast.lesson.service;

import java.util.List;

import com.zltel.broadcast.lesson.bean.LessonCategory;

public interface LessonCategoryService {
    int insertSelective(LessonCategory record);

    LessonCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LessonCategory record);

    int deleteByPrimaryKey(Integer pk);
    
    List<LessonCategory> query(LessonCategory record);
}
