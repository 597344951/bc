package com.zltel.broadcast.template.bean;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.broadcast.common.codegenerator.MyBatisScriptCreateUtil;

public class TemplateContentTest {
    public static final Logger logout = LoggerFactory.getLogger(TemplateTypeTest.class);

    @Test
    public void testWhereTemplateContent() throws Exception {
        StringBuilder sb = MyBatisScriptCreateUtil.createWhereScript(TemplateContent.class);
        logout.info(TemplateType.class.getName() +" _> WHERE: \n"+sb.toString());
    }
}
