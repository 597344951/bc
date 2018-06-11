package com.zltel.broadcast.common.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zltel.broadcast.common.dao.SimpleDao;
import com.zltel.broadcast.common.logger.LogPersistenceServlet;

/**
 * 日志收集配置
 * LogConfiguration class
 *
 * @author Touss
 * @date 2018/5/4
 */
@Configuration
public class LogConfiguration {
    @Autowired
    SimpleDao simpleDao;
    @Bean
    public ServletRegistrationBean logPersistenceServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new LogPersistenceServlet(simpleDao),"/loghandle/*");

        //系统启动时同时初始化该servlet
        servletRegistrationBean.setLoadOnStartup(0);
        return servletRegistrationBean;
    }
}
