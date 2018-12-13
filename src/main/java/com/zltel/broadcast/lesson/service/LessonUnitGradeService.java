package com.zltel.broadcast.lesson.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonUnitGrade;

public interface LessonUnitGradeService extends BaseDao<LessonUnitGrade> {
    public List<LessonUnitGrade> selectListByLessonUnitId(Integer lessonUnitId);
}
