package com.zltel.broadcast.lesson.service;

import java.util.List;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.lesson.bean.LessonUnit;

public interface LessonUnitService {

    int insertSelective(LessonUnit record);

    LessonUnit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LessonUnit record);

    int deleteByPrimaryKey(Integer pk);

    List<LessonUnit> query(LessonUnit record);

    /**
     * 查询目录相关 以及 符合限制学习组织的课题数据
     * 
     * @param record
     * @return
     * @junit {@link LessonUnitServiceTest#testQueryRelatedData()}
     */
    List<LessonUnit> queryCategoryRelatedData(LessonUnit record,Pager pager);
}
