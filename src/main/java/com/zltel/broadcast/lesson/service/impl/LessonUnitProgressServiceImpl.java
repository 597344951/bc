package com.zltel.broadcast.lesson.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.lesson.bean.LessonSection;
import com.zltel.broadcast.lesson.bean.LessonUnitProgress;
import com.zltel.broadcast.lesson.dao.LessonUnitProgressDAO;
import com.zltel.broadcast.lesson.service.LessonSectionService;
import com.zltel.broadcast.lesson.service.LessonUnitProgressService;

@Service
public class LessonUnitProgressServiceImpl extends BaseDaoImpl<LessonUnitProgress>
        implements
            LessonUnitProgressService {

    @Resource
    private LessonUnitProgressDAO progressDao;

    @Resource
    private LessonSectionService sectionService;

    @Override
    public BaseDao<LessonUnitProgress> getInstince() {
        return progressDao;
    }

    @Override
    @Transactional
    public void recordProgress(LessonUnitProgress record) {
        record.setSaveTime(new Date());
        LessonSection lessonSection = this.sectionService.selectByPrimaryKey(record.getLessonId());
        if (null == lessonSection) throw RRException.makeThrow("课程不存在");
        // 课程总 学识
        Integer totalHours = lessonSection.getCreditHours();
        List<LessonUnitProgress> list = this.progressDao.query(record);
        if (list.isEmpty()) {
            this.progressDao.insertSelective(record);
        } else {
            LessonUnitProgress lup = list.get(0);
            lup.setPlayProgress(record.getPlayProgress());
            Integer targetHours = lup.getCreditHours() == null ? 0 : lup.getCreditHours() + record.getCreditHours();
            lup.setCreditHours(targetHours >= totalHours ? totalHours : targetHours);
            lup.setSaveTime(new Date());
            this.progressDao.updateByPrimaryKeySelective(lup);
        }
    }

}
