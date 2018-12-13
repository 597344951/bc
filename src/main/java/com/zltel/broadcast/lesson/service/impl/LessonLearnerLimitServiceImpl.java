package com.zltel.broadcast.lesson.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.lesson.bean.LessonLearnerLimit;
import com.zltel.broadcast.lesson.dao.LessonLearnerLimitDAO;
import com.zltel.broadcast.lesson.service.LessonLearnerLimitService;

@Service
public class LessonLearnerLimitServiceImpl implements LessonLearnerLimitService {

    @Resource
    private LessonLearnerLimitDAO limitDao;

    @Override
    public int insertSelective(LessonLearnerLimit record) {
        return this.limitDao.insertSelective(record);
    }

    @Override
    public LessonLearnerLimit selectByPrimaryKey(Integer id) {
        return this.limitDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(LessonLearnerLimit record) {
        return this.limitDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Integer pk) {
        return this.limitDao.deleteByPrimaryKey(pk);
    }

    @Override
    public List<LessonLearnerLimit> query(LessonLearnerLimit record) {
        return this.limitDao.query(record);
    }

    @Override
    public List<LessonLearnerLimit> selectListByLessonUnitId(Integer lessonUnitId) {
        return this.limitDao.selectListByLessonUnitId(lessonUnitId);
    }

}
