package com.zltel.broadcast.example.logout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/****
 * Reason: 获取 日志输出对象的方法
 */
public class GetLogger {
    public static Logger logger = LoggerFactory.getLogger(GetLogger.class);

    public void test(){
        logger.info("logger out put message");
    }
}
