package com.zltel.broadcast.publish;

import com.zltel.broadcast.publish.service.PublishService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * LocalScheduled class
 *
 * @author Touss
 * @date 2018/5/24
 */
@Component
public class PublishSchedule {

    private static final Log log = LogFactory.getLog(PublishSchedule.class);

    @Autowired
    private PublishService publishService;
    @Scheduled(cron = "0 0 5 * * ?")
    public void offline() {
        int count = publishService.offline();
        log.info("节目过期下线处理：" + count);
    }
}
