package com.zltel.broadcast.common.configuration;

import org.springframework.web.servlet.resource.ResourceUrlProvider;

import com.zltel.broadcast.common.util.SpringContextUtils;

public class ResourceProviderConfig {
    
    public static ResourceUrlProvider getResourceProvider() {
        return SpringContextUtils.getBean(ResourceUrlProvider.class);
    }
}
