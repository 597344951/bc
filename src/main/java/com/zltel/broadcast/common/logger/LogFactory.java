package com.zltel.broadcast.common.logger;

import java.lang.management.ManagementFactory;

/**
 * LogFactory class
 *
 * @author Touss
 * @date 2018/5/4
 */
public class LogFactory {
    private static String pid;

    static {
        pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    public static Logger getLog(Class<?> clazz, String username, String feature) {
        return new Logger(clazz, pid, username, feature);
    }

    public static Logger getLog(Class<?> clazz, String feature) {
        return new Logger(clazz, pid, "anonymous", feature);
    }
}
