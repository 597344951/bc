package com.zltel.broadcast.lesson.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonSection;

/**
 * LessonSectionDAO继承基类
 */
@Repository
public interface LessonSectionDAO extends BaseDao<LessonSection> {
    
    List<LessonSection> selectListByLessonUnitId(Integer lessonUnitId);
}