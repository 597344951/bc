package com.zltel.broadcast.lesson.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonUnitTest;

/**
 * LessonUnitTestDAO继承基类
 */
@Repository
public interface LessonUnitTestDAO extends BaseDao<LessonUnitTest> {

    List<LessonUnitTest> selectListByLessonUnitId(Integer lessonUnitId);
}
