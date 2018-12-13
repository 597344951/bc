package com.zltel.broadcast.report.bean;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptMain;

@Generated(value = "org.junit-tools-1.0.6")
public class ReportSubmitTypeTest extends BaseTests {

    @Test
    public void testGetEtime() throws Exception {
        StringBuilder sb = MyBatisScriptMain.createCRUDScript(ReportSubmitType.class);
        logout.info(sb.toString());
    }
}
