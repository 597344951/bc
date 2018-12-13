package com.zltel.broadcast.lesson.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.lesson.bean.LessonUnit;
import com.zltel.broadcast.lesson.bean.LessonUnitRegistration;
import com.zltel.broadcast.lesson.dao.LessonUnitDAO;
import com.zltel.broadcast.lesson.dao.LessonUnitRegistrationMapper;
import com.zltel.broadcast.lesson.service.LessonUnitRegistrationService;

@Service
public class LessonUnitRegistrationServiceImpl extends BaseDaoImpl<LessonUnitRegistration>
        implements
            LessonUnitRegistrationService {

    @Resource
    private LessonUnitRegistrationMapper regDao;
    @Resource
    private LessonUnitDAO lessonUnitDao;

    @Override
    public BaseDao<LessonUnitRegistration> getInstince() {
        return regDao;
    }

    @Override
    public void registLesson(LessonUnitRegistration reg) {
        LessonUnit lu = this.lessonUnitDao.selectByPrimaryKey(reg.getLessonUnitId());
        if(lu == null)throw RRException.makeThrow("要报名的课程不存在");
        List<LessonUnitRegistration> list = this.regDao.query(reg);
        if(list.isEmpty()) {
            reg.setAddTime(new Date());
            this.regDao.insert(reg);
        }else {
            throw RRException.makeThrow("已报名该课程");
        }
    }


}
