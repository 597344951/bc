package com.zltel.broadcast.learncenter.bean;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptMain;

@Generated(value = "org.junit-tools-1.0.6")
public class LearnCenterHistoryTest extends BaseTests {


    @Test
    public void testGetId() throws Exception {
        StringBuilder sb = MyBatisScriptMain.createCRUDScript(LearnCenterHistory.class);
        logout.info(sb.toString());
    }
}
