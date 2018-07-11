package com.zltel.broadcast.report.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.report.bean.ReportSubmit;

public class ReportsubmitServiceTest extends BroadcastApplicationTests {

    @Resource
    private ReportsubmitService service;

    @Test
    public void testQuery() {
        ReportSubmit record = new ReportSubmit();
        Pager prb = new Pager(1, 10);
        Object data = this.service.query(record, prb);
        logout.info("query: {},{}", JSON.toJSONString(prb), JSON.toJSONString(data));
    }

}
