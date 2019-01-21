package com.zltel.broadcast.lesson.service;

import java.util.List;

import com.zltel.broadcast.lesson.bean.LessonLearnerLimit;

public interface LessonLearnerLimitService {
    int insertSelective(LessonLearnerLimit record);

    LessonLearnerLimit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LessonLearnerLimit record);

    int deleteByPrimaryKey(Integer pk);

    List<LessonLearnerLimit> query(LessonLearnerLimit record);

    List<LessonLearnerLimit> selectListByLessonUnitId(Integer lessonUnitId);

    /**
     * 配置课程限定组织
     * 
     * @param list
     */
    void configOrgLimit(List<LessonLearnerLimit> list);
}
