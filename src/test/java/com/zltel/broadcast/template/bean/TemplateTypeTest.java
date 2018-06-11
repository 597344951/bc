package com.zltel.broadcast.template.bean;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zltel.broadcast.common.codegenerator.MyBatisScriptCreateUtil;

public class TemplateTypeTest {
    
    public static final Logger logout = LoggerFactory.getLogger(TemplateTypeTest.class);


    /**生成TemplateType查询Where 代码**/
    @Test
    public void testWhereCode() throws Exception {
        StringBuffer sb = MyBatisScriptCreateUtil.createWhereScript(TemplateType.class);
        logout.info(TemplateType.class.getName() +" _> WHERE: \n"+sb.toString());
    }
}
