package com.zltel.broadcast.template.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
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
        record.setOrgid(6);
        //record.setTpTypeId(5);
        List<TemplateContent> result;
        
        Pager pager = Pager.DEFAULT_PAGER;
        // default test
        testSubject = createTestSubject();
        result = testSubject.queryByType(record,pager);
        logout.info("分页对象: ",JSON.toJSONString(pager));
        logout.info("查询分类:{} , 下模版: {}",record.getOrgid() ,JSON.toJSONString(result));
    }
}
