package com.zltel.broadcast.incision.sola.utils;


import javax.annotation.Generated;

import org.junit.Test;

import com.zltel.BaseTests;

@Generated(value = "org.junit-tools-1.0.6")
public class ResExtractUtilTest extends BaseTests {

    @Test
    public void testRepResDomain() throws Exception {
        String resUrl = "http://192.168.1.1/test/123.png";
        String domain = "http://192.168.1.1:23333/abc.png";
        String result;

        // default test
        result = ResExtractUtil.repResDomain(resUrl, domain);
        logout.info(result);
    }
}
