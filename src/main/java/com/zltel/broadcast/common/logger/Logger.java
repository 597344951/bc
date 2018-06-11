package com.zltel.broadcast.common.logger;

import org.apache.commons.logging.*;

import java.util.Date;



/**
 * 自定义日志收集器
 * Logger class
 *
 * @author Touss
 * @date 2018/5/3
 */
public class Logger {


    private Class clazz;
    private String pid;
    private String username;
    private String feature;

    public Logger(Class clazz, String pid, String username, String feature) {
        this.clazz = clazz;
        this.pid = pid;
        this.username = username;
        this.feature = feature;
    }

    public void log(String level, String msg) {
        LogQueue.add(new LogBean(level, clazz.getName(), new Date(), msg,  username, feature));
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void debug(String msg) {
        log(Level.LEVEL_DEBUG, msg);
    }

    public void info(String msg) {
        log(Level.LEVEL_INFO, msg);
    }

    public void warn(String msg) {
        log(Level.LEVEL_WARN, msg);
    }

    public void error(String msg) {
        log(Level.LEVEL_ERROR, msg);
    }

    public void fatal(String msg) {
        log(Level.LEVEL_FATAL, msg);
    }
}
