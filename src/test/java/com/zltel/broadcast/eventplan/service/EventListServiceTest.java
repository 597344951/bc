package com.zltel.broadcast.eventplan.service;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.eventplan.bean.EventList;

@Generated(value = "org.junit-tools-1.0.6")
public class EventListServiceTest extends BroadcastApplicationTests {
     @Resource
     private EventListService service;

    @Test
    public void testQueryInTime() throws Exception {
        EventList record = new EventList();
        record.setOrgId(6);
        record.setStime(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3)));
        
        String[] statuss = {"0"};
        String[] froms = {"system","user"};
        String[] prioritys = {"1","2"};
        record.setStatuss(statuss);
        record.setFroms(froms);
        record.setPrioritys(prioritys);
        
        List<EventList> result = this.service.queryInTime(record);
        logout.info("查询在指定时间事件{}",JSON.toJSONString(result));
    }
}
