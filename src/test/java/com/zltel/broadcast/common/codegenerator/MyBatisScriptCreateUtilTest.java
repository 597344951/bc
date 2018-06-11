package com.zltel.broadcast.common.codegenerator;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;

@Generated(value = "org.junit-tools-1.0.6")
public class MyBatisScriptCreateUtilTest extends BaseTests{

    private MyBatisScriptCreateUtil createTestSubject() {
        return new MyBatisScriptCreateUtil();
    }

    @Test
    public void testToDataBaseName() throws Exception {
        String fn = "ChinaBank";
        String result;

        // default test
        result = MyBatisScriptCreateUtil.toDataBaseName(fn);
        logout.info("转换结果{}->{}",fn,result);
    }
}
