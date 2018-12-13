package com.zltel.broadcast.lesson.service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonUnitProgress;

public interface LessonUnitProgressService extends BaseDao<LessonUnitProgress> {
    /**记录播放进度**/
    void recordProgress(LessonUnitProgress record);

}
