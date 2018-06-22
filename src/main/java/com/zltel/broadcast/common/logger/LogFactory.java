package com.zltel.broadcast.common.logger;

/**
 * LogFactory class
 *
 * @author Touss
 * @date 2018/5/4
 */
public class LogFactory {
    private LogFactory() {}


    public static Logger getLog(Class<?> clazz, String username, String feature) {
        return new Logger(clazz, username, feature);
    }

    public static Logger getLog(Class<?> clazz, String feature) {
        return new Logger(clazz, "anonymous", feature);
    }
}
