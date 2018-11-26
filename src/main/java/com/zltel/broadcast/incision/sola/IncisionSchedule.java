package com.zltel.broadcast.incision.sola;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.terminal.service.TerminalBasicInfoService;

/**
 * LocalScheduled class
 *
 * @author Touss
 * @date 2018/5/21
 */
@Component
public class IncisionSchedule {


    private static final Logger log = LoggerFactory.getLogger(IncisionSchedule.class);

    
    @Resource
    private TerminalBasicInfoService terminalService;

    @Scheduled(cron = "0 30 * * * ?")
    public void terminalSync() {
        try {
            terminalService.synchronizTerminalInfo();
            log.info("终端数据同步完成..");
        } catch (Exception e) {
            log.error("终端信息同步失败：{}", e);
        }
    }
}
