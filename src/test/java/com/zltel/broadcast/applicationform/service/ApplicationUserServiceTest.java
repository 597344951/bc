package com.zltel.broadcast.applicationform.service;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.applicationform.bean.ApplicationUser;

@Generated(value = "org.junit-tools-1.0.6")
public class ApplicationUserServiceTest extends BroadcastApplicationTests {

    @Resource
    private ApplicationUserService service;

    @Test
    public void testQueryFull() throws Exception {
        ApplicationUser record = new ApplicationUser();
        List<ApplicationUser> result = this.service.queryFull(record);
        logout.info("{}", JSON.toJSONString(result));
    }
}
