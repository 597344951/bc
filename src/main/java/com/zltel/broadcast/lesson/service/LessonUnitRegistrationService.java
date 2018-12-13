package com.zltel.broadcast.lesson.service;

import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonUnitRegistration;

public interface LessonUnitRegistrationService extends BaseDao<LessonUnitRegistration> {
    /**
     * 报名某个课程
     * 
     * @param reg
     */
    @Transactional
    void registLesson(LessonUnitRegistration reg);

}
