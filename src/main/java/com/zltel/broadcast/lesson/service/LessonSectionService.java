package com.zltel.broadcast.lesson.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonSection;

public interface LessonSectionService extends BaseDao<LessonSection> {

    List<LessonSection> selectListByLessonUnitId(Integer lessonUnitId);

}
