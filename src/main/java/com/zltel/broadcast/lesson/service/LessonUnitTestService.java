package com.zltel.broadcast.lesson.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonUnitTest;

public interface LessonUnitTestService extends BaseDao<LessonUnitTest> {
    List<LessonUnitTest> selectListByLessonUnitId(Integer lessonUnitId);
}
