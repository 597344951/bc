package com.zltel.broadcast.common.configuration;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 分离配置生命周期bean,如果和Shiro一起配置,否则会导致配置文件等信息无法注入 **/
@Configuration
public class ShiroLifecycleBeanPostProcessorConfig {

    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
