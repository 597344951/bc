package com.zltel.broadcast.eventplan.bean;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptMain;

@Generated(value = "org.junit-tools-1.0.6")
public class CostPlanTest extends BaseTests {

     
    @Test
    public void test() throws Exception {
        StringBuilder sb = MyBatisScriptMain.createCRUDScript(CostPlan.class);
        logout.info(sb.toString());
    }
}
