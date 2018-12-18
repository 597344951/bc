package com.zltel.broadcast.lesson.service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.lesson.bean.LessonUnitProgress;

public interface LessonUnitProgressService extends BaseDao<LessonUnitProgress> {
    /**记录播放进度**/
    void recordProgress(LessonUnitProgress record);
    /**获取用户指定课程进度情况，优先返回redis中缓存的情况**/
    LessonUnitProgress getLessonUnitProgress(LessonUnitProgress record);

}
