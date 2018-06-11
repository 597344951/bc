package com.zltel.broadcast.template.service.impl;


import java.util.List;

import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zltel.BroadcastApplicationTests;
import com.zltel.broadcast.template.bean.TemplateType;
import com.zltel.broadcast.template.bean.TemplateTypeTreeNode;
import com.zltel.broadcast.template.service.TemplateTypeService;

public class TemplateTypeServiceImplTest extends BroadcastApplicationTests {
    @Resource
    private TemplateTypeService templateTypeService;


    public void testQueryEachCount() throws Exception {
        List<TemplateType> result = null;
        TemplateType tp = new TemplateType();
        tp.setOrgid(0);
        result = templateTypeService.queryEachCount(tp);
        logout.info(JSON.toJSONString(result));
    }

    @Test
    public void testGetTypeTree() throws Exception {
        TemplateType tp = new TemplateType();
        tp.setOrgid(1);
        List<TemplateTypeTreeNode> result = this.templateTypeService.getTypeTree(tp);
        logout.info(JSON.toJSONString(result));
    }
}

