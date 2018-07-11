package com.zltel.broadcast.report.service;


import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.report.bean.ReportType;

@Generated(value = "org.junit-tools-1.0.6")
public class ReportTypeServiceTest extends BroadcastApplicationTests {

    @Resource
    private ReportTypeService service;

    @Test
    public void testQueryReportTemplateCount() throws Exception {
        ReportType record = new ReportType();
        record.setOrgid(6);
        
        Object datas = this.service.queryReportTemplateCount(record);
        logout.info("查询报告类型,包含模版统计数 {}",JSON.toJSONString(datas));
    }
}
