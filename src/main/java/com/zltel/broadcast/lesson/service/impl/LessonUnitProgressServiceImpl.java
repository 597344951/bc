package com.zltel.broadcast.lesson.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource
    RedisTemplate<Serializable, LessonUnitProgress> redisTemplate;

    @Override
    public BaseDao<LessonUnitProgress> getInstince() {
        return progressDao;
    }

    @Override
    @Transactional
    public void recordProgress(LessonUnitProgress record) {
        record.setSaveTime(new Date());
        String key = "LessonUnitProgress-" + record.getUserId() + "-" + record.getLessonId();
        LessonUnitProgress cp = this.redisTemplate.opsForValue().get(key);
        if (cp == null) {
            List<LessonUnitProgress> list = this.progressDao.query(record);
            if (!list.isEmpty()) {
                record.setCreditHours(list.get(0).getCreditHours());
            }
            // 如果redis缓存记录中不存在，将此记录保存到redis中，设置超时时间为2分钟
            this.redisTemplate.opsForValue().set(key, record, 2, TimeUnit.MINUTES);
            return;
        }
        // 如果是 播放结束时回调, 直接调用保存
        if (record.getEndToSave() == null || !record.getEndToSave()) {
            if (record.getSaveTime().getTime() - cp.getSaveTime().getTime() < TimeUnit.MINUTES.toMillis(1)) {
                return;
            }
        }

        LessonSection lessonSection = this.sectionService.selectByPrimaryKey(record.getLessonId());
        if (null == lessonSection) throw RRException.makeThrow("课程不存在");
        // 课程总 学时
        Integer totalHours = lessonSection.getCreditHours();
        List<LessonUnitProgress> list = this.progressDao.query(record);
        if (list.isEmpty()) {
            record.setCreditHours(1);
            this.progressDao.insertSelective(record);
        } else {
            LessonUnitProgress lup = list.get(0);
            lup.setPlayProgress(record.getPlayProgress());
            Integer targetHours = lup.getCreditHours() == null ? 0 : lup.getCreditHours() + 1;
            lup.setCreditHours(targetHours >= totalHours ? totalHours : targetHours);
            lup.setSaveTime(new Date());
            this.progressDao.updateByPrimaryKeySelective(lup);

            record.setCreditHours(lup.getCreditHours());
        }
        this.redisTemplate.opsForValue().set(key, record, 2, TimeUnit.MINUTES);
    }


    @Override
    public LessonUnitProgress getLessonUnitProgress(LessonUnitProgress record) {
        if (record == null) return record;
        String key = "LessonUnitProgress-" + record.getUserId() + "-" + record.getLessonId();
        LessonUnitProgress cp = this.redisTemplate.opsForValue().get(key);
        if (cp != null) return cp;
        List<LessonUnitProgress> list = this.progressDao.query(record);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

}
