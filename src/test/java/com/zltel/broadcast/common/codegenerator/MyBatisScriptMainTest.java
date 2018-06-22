package com.zltel.broadcast.common.codegenerator;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.template.bean.TemplateType;

@Generated(value = "org.junit-tools-1.0.6")
public class MyBatisScriptMainTest extends BaseTests{


    @Test
    public void testCreateCRUDScript() throws Exception {
        Class<TemplateType> c = TemplateType.class;
        StringBuilder result;

        // default test
        result = MyBatisScriptMain.createCRUDScript(c);
        logout.info("CRUD : {}",result);
    }
}
