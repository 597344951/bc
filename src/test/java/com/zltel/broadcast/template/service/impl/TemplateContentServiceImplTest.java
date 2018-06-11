package com.zltel.broadcast.template.service.impl;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.template.bean.TemplateContent;
import com.zltel.broadcast.template.service.TemplateContentService;

public class TemplateContentServiceImplTest extends BroadcastApplicationTests {
    @Resource
    private TemplateContentService service;

    private TemplateContentService createTestSubject() {
        return this.service;
    }

    @Test
    public void testQueryByType() throws Exception {
        TemplateContentService testSubject;
        TemplateContent record = new TemplateContent();
        record.setOrgid(1);
        record.setTpTypeId(5);
        List<TemplateContent> result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.queryByType(record);
        logout.info("查询分类:" + record.getOrgid() + " 下的文章 -> " + JSON.toJSONString(result));
    }
}
