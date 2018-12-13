package com.zltel.broadcast.lesson.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonLearnerLimit;

/**
 * LessonLearnerLimitDAO继承基类
 */
@Repository
public interface LessonLearnerLimitDAO extends BaseDao<LessonLearnerLimit> {
    
    List<LessonLearnerLimit> selectListByLessonUnitId(Integer lessonUnitId);
}