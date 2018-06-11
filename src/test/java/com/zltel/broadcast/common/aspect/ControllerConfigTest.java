package com.zltel.broadcast.common.aspect;


import javax.annotation.Generated;
import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import com.zltel.BroadcastApplicationTests;

@Generated(value = "org.junit-tools-1.0.6")
public class ControllerConfigTest extends BroadcastApplicationTests {
    
    @Resource
    ResourceUrlProvider resourceUrlProvider;


    @Test
    public void testUrls() throws Exception {
        String url = "/assets/css/common.css";
        String lp = this.resourceUrlProvider.getForLookupPath(url);
        logout.info("load {} -> {}",url,lp);
    }
}
