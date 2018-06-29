package com.zltel.broadcast.template.service;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageRowBounds;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.template.bean.TemplateContent;

@Generated(value = "org.junit-tools-1.0.6")
public class TemplateContentServiceTest extends BroadcastApplicationTests {

    @Resource
    private TemplateContentService service;

    @Test
    public void testQueryByType() throws Exception {
        TemplateContent record = new TemplateContent();
        record.setKeyword("");
        PageRowBounds prb = Pager.DEFAULT_PAGER;
        List<TemplateContent> result = this.service.queryByType(record, prb);
        logout.info("模糊查询, pager: {}, result:{}", JSON.toJSONString(prb), JSON.toJSONString(result));
    }
}
