package com.zltel.broadcast.eventplan.bean;


import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptMain;

public class EventListTest extends BaseTests {



    @Test
    public void testSetTitle() throws Exception {
        StringBuffer sb = MyBatisScriptMain.createCRUDScript(EventList.class);
        logout.info(sb.toString());
    }
}
