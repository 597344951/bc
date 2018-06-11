package com.zltel.broadcast.common.configuration;


import javax.annotation.Generated;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import com.zltel.BroadcastApplicationTests;

@Generated(value = "org.junit-tools-1.0.6")
public class ResourceProviderConfigTest extends BroadcastApplicationTests {

    @Test
    public void testGetInstince() throws Exception {
        ResourceUrlProvider result;

        // default test
        result = ResourceProviderConfig.getResourceProvider();
        
        Assert.assertNotNull(result);
    }
}
