package com.zltel.broadcast.lesson.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonUnit;

/**
 * LessonUnitDAO继承基类
 */
@Repository
public interface LessonUnitDAO extends BaseDao<LessonUnit> {
    List<LessonUnit> query(LessonUnit record);
    
    List<LessonUnit> queryRelatedData(LessonUnit record);
}