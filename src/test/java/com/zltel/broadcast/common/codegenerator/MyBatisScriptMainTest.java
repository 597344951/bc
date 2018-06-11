package com.zltel.broadcast.common.codegenerator;


import javax.annotation.Generated;

import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.template.bean.TemplateType;

@Generated(value = "org.junit-tools-1.0.6")
public class MyBatisScriptMainTest extends BaseTests{

    private MyBatisScriptMain createTestSubject() {
        return new MyBatisScriptMain();
    }

    @Test
    public void testCreateCRUDScript() throws Exception {
        Class<TemplateType> c = TemplateType.class;
        StringBuffer result;

        // default test
        result = MyBatisScriptMain.createCRUDScript(c);
        logout.info("CRUD : {}",result);
    }
}
