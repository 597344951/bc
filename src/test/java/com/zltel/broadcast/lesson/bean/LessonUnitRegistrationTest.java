package com.zltel.broadcast.lesson.bean;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptMain;

@Generated(value = "org.junit-tools-1.0.6")
public class LessonUnitRegistrationTest extends BaseTests {

    @Test
    public void testGetAddTime() throws Exception {
        StringBuilder sb = MyBatisScriptMain.createCRUDScript(LessonUnitRegistration.class);
        logout.info(sb.toString());
    }
}
