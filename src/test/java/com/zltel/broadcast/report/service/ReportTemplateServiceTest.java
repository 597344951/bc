package com.zltel.broadcast.report.service;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.report.bean.ReportTemplate;

public class ReportTemplateServiceTest extends BroadcastApplicationTests {

    @Resource
    private ReportTemplateService service;

    @Test
    public void testQuery() {
        Pager prb = new Pager(1, 10);
        ReportTemplate record = new ReportTemplate();
        Object data = this.service.query(record, prb);
        logout.info("query: {},{}",JSON.toJSONString(prb),JSON.toJSONString(data));
    }
}
