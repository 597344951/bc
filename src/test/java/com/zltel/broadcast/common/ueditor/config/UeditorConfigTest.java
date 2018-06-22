package com.zltel.broadcast.common.ueditor.config;


import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;

import com.zltel.BroadcastApplicationTests;

@Generated(value = "org.junit-tools-1.0.6")
public class UeditorConfigTest extends BroadcastApplicationTests {
    
    @Resource
    private UeditorConfig instince;

    @Test
    public void testGetSavepath() throws Exception {
        String result;

        // default test
        result = instince.getSavepath();
        logout.info("savePath: {}",result);
    }
}
