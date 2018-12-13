package com.zltel.broadcast.learncenter.service;


import java.util.Date;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.learncenter.bean.LearnCenterHistory;
import com.zltel.broadcast.learncenter.bean.LearningProgress;

@Generated(value = "org.junit-tools-1.0.6")
public class LearnCenterHistoryServiceTest extends BroadcastApplicationTests {

    @Resource
    private LearnCenterHistoryService service;

    @Test
    public void testQuery() throws Exception {
        LearnCenterHistory record = new LearnCenterHistory();
        record.setTime(new Date());
        this.service.query(record);
    }

    @Test
    public void testLearningProcess() {
        LearnCenterHistory record = new LearnCenterHistory();
        LearningProgress lp = this.service.learningProcess(record);
        logout.info("学习进度:{}", JSON.toJSONString(lp));
    }
}
