package com.zltel.broadcast.common.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "my.config")
public class TestConfig {
    /**测试名称**/
    private static String name = "test";

    /**
     * 获取name  
     * @return the name
     */
    public static String getName() {
        return name;
    }
    

    /**
     * 设置name  
     * @param name the name to set
     */
    public static void setName(String name) {
        TestConfig.name = name;
    }
    
    

}
