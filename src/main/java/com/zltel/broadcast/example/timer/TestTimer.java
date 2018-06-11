package com.zltel.broadcast.example.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestTimer {
    
    /**日志输出对象**/
    public static final Logger logger = LoggerFactory.getLogger(TestTimer.class);
    
    @Scheduled(cron = "0 0/10 * * * ?")
    public void testTimer(){
        logger.info("测试定时器 定时任务。");
    }
}
