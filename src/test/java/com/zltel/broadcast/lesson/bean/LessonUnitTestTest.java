package com.zltel.broadcast.lesson.bean;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptMain;

public class LessonUnitTestTest extends BaseTests {

    @Test
    public void test() {
        StringBuilder sb = MyBatisScriptMain.createCRUDScript(LessonUnitTest.class);
        logout.info(sb.toString());
    }

}
