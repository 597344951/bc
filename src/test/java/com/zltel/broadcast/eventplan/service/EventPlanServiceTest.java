package com.zltel.broadcast.eventplan.service;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;

@Generated(value = "org.junit-tools-1.0.6")
public class EventPlanServiceTest extends BroadcastApplicationTests {

    @Resource
    private EventPlanService eventPlanService;


    @Test
    public void testQuery() {
        Pager prb = new Pager(1, 10);
        EventPlanInfo record = new EventPlanInfo();
        List<EventPlanInfo> records = this.eventPlanService.query(record, prb);
        
        logout.info("查詢結果: {},{}",JSON.toJSONString(records),JSON.toJSONString(prb));
    }
}
