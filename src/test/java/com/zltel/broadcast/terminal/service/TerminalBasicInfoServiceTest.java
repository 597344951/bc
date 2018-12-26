package com.zltel.broadcast.terminal.service;


import java.util.Map;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;

@Ignore
@Generated(value = "org.junit-tools-1.0.6")
public class TerminalBasicInfoServiceTest extends BroadcastApplicationTests {

    @Resource
    private TerminalBasicInfoService servie;

    @Test
    public void testCountOnlineTerminal() throws Exception {
        Map<String, Integer> result = this.servie.countOnlineTerminal(null);
        logout.info(JSON.toJSONString(result));
    }

    @Test
    public void testSynchronizTerminalInfo() {
        this.servie.synchronizTerminalInfo();
    }

    @Test
    public void testQueryBasicInfo() {
        TerminalBasicInfo record = new TerminalBasicInfo();
        R r = this.servie.queryBasicInfo(record, 1, 10);
        logout.info("终端信息：{}", JSON.toJSONString(r));
    }
}
