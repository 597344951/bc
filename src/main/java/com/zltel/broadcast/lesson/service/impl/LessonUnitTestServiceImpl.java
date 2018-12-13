package com.zltel.broadcast.lesson.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.lesson.bean.LessonUnitTest;
import com.zltel.broadcast.lesson.dao.LessonUnitTestDAO;
import com.zltel.broadcast.lesson.service.LessonUnitTestService;

@Service
public class LessonUnitTestServiceImpl extends BaseDaoImpl<LessonUnitTest> implements LessonUnitTestService {

    @Resource
    private LessonUnitTestDAO testDao;
    @Override
    public BaseDao<LessonUnitTest> getInstince() {
       return testDao;
    }
    @Override
    public List<LessonUnitTest> selectListByLessonUnitId(Integer lessonUnitId) {
        return this.testDao.selectListByLessonUnitId(lessonUnitId);
    }

}
