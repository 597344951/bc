package com.zltel.broadcast.terminal.service;


import java.util.Map;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;

@Generated(value = "org.junit-tools-1.0.6")
public class TerminalBasicInfoServiceTest extends BroadcastApplicationTests {

    @Resource
    private TerminalBasicInfoService servie;

    @Test
    public void testCountOnlineTerminal() throws Exception {
        Map<String, Integer> result = this.servie.countOnlineTerminal();
        logout.info(JSON.toJSONString(result));
    }
}
