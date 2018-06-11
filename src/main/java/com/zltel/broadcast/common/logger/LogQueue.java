package com.zltel.broadcast.common.logger;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 日志缓存器
 * LogQueue class
 *
 * @author Touss
 * @date 2018/5/4
 */
public class LogQueue {
    /**
     * 日志缓存
     */
    private static LinkedBlockingQueue<LogBean> queue = new LinkedBlockingQueue<LogBean>(1000);

    public static void add(LogBean log) {
        queue.add(log);
    }

    public static LogBean pop() {
        return queue.poll();
    }
}
