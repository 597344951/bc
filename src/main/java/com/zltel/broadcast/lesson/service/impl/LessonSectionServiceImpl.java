package com.zltel.broadcast.lesson.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.lesson.bean.LessonSection;
import com.zltel.broadcast.lesson.dao.LessonSectionDAO;
import com.zltel.broadcast.lesson.service.LessonSectionService;

@Service
public class LessonSectionServiceImpl extends BaseDaoImpl<LessonSection> implements LessonSectionService {

    @Resource
    private LessonSectionDAO sectionDao;

    @Override
    public BaseDao<LessonSection> getInstince() {
        return sectionDao;
    }

    @Override
    public List<LessonSection> selectListByLessonUnitId(Integer lessonUnitId) {

        return this.sectionDao.selectListByLessonUnitId(lessonUnitId);
    }

}
