package com.zltel.broadcast.applicationform.service;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.applicationform.bean.ApplicationForm;

@Generated(value = "org.junit-tools-1.0.6")
public class ApplicationFormServiceTest extends BroadcastApplicationTests {

    @Resource
    private ApplicationFormService service;

    @Test
    public void testQueryFull() throws Exception {
        ApplicationForm record = new ApplicationForm();
        List<ApplicationForm> result = this.service.queryFull(record);
        logout.info("{}", JSON.toJSONString(result));
    }
}
