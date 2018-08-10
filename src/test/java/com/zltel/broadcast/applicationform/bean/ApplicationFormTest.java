package com.zltel.broadcast.applicationform.bean;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptMain;

@Generated(value = "org.junit-tools-1.0.6")
public class ApplicationFormTest extends BaseTests {


    @Test
    public void test() throws Exception {
        StringBuilder sb = MyBatisScriptMain.createCRUDScript(ApplicationForm.class);
        logout.info(sb.toString());
    }
}
