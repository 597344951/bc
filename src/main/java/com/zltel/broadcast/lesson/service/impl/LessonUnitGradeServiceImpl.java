package com.zltel.broadcast.lesson.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.lesson.bean.LessonUnitGrade;
import com.zltel.broadcast.lesson.dao.LessonUnitGradeDAO;
import com.zltel.broadcast.lesson.service.LessonUnitGradeService;

@Service
public class LessonUnitGradeServiceImpl extends BaseDaoImpl<LessonUnitGrade> implements LessonUnitGradeService {

    @Resource
    private LessonUnitGradeDAO gradeDao;

    @Override
    public BaseDao<LessonUnitGrade> getInstince() {
        return gradeDao;
    }

    @Override
    public List<LessonUnitGrade> selectListByLessonUnitId(Integer lessonUnitId) {
        throw RRException.makeThrow("未实现");
    }


}
