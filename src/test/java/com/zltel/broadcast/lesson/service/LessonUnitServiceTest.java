package com.zltel.broadcast.lesson.service;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.lesson.bean.LessonUnit;

@Generated(value = "org.junit-tools-1.0.6")
public class LessonUnitServiceTest extends BroadcastApplicationTests {

    @Resource
    private LessonUnitService service;

    @Test
    public void testQueryRelatedData() throws Exception {
        LessonUnit record = new LessonUnit();
        record.setKeyword("学习");
        List<LessonUnit> lu = this.service.queryCategoryRelatedData(record);
        logout.info(JSON.toJSONString(lu));
    }
}
