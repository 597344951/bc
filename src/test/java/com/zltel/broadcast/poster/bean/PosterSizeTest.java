package com.zltel.broadcast.poster.bean;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;
import com.zltel.broadcast.common.codegenerator.MyBatisScriptCreateUtil;

@Generated(value = "org.junit-tools-1.0.6")
public class PosterSizeTest extends BaseTests {



    @Test
    public void test() throws Exception {

        StringBuilder sb = MyBatisScriptCreateUtil.createWhereParamsScript(PosterSize.class);
        logout.info(sb.toString()   );
    }
}
