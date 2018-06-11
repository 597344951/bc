package com.zltel.broadcast.incision.sola;

import com.zltel.broadcast.common.dao.SimpleDao;
import com.zltel.broadcast.incision.sola.service.SolaProgramService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LocalScheduled class
 *
 * @author Touss
 * @date 2018/5/21
 */
@Component
public class IncisionSchedule {

    private static final Log log = LogFactory.getLog(IncisionSchedule.class);

    @Autowired
    private SolaProgramService solaProgramService;
    @Autowired
    private SimpleDao simpleDao;

    @Scheduled(cron = "0 30 5 * * ?")
    public void terminalSync() {
        try {
            List<Map<String, Object>> terminals = solaProgramService.queryTerminal();
            if(terminals != null && terminals.size() > 0) {
                Map<String, Object> terminal = null;
                Map<String, Object> queryParam = new HashMap<String, Object>();
                for(Map<String, Object> t : terminals) {
                    terminal = new HashMap<String, Object>();
                    terminal.put("name", t.get("Name"));
                    terminal.put("id", t.get("PkId"));
                    terminal.put("code", t.get("Code"));
                    terminal.put("type_id", t.get("ScreenType"));
                    terminal.put("res_time", t.get("RegDateTime"));
                    terminal.put("online", t.get("OnlineStatus"));
                    terminal.put("last_time", t.get("LastOnlineTime"));
                    terminal.put("ip", t.get("IP"));
                    terminal.put("mac", t.get("Mac"));
                    terminal.put("size", t.get("ScreenSize"));
                    terminal.put("ratio", t.get("Resolution"));
                    terminal.put("rev", t.get("ScreenDirection"));
                    terminal.put("ver", t.get("Version"));
                    terminal.put("typ", t.get("ScreenInteraction"));
                    terminal.put("addr", t.get("Position"));

                    queryParam.put("id", t.get("PkId"));
                    if(simpleDao.get("terminal_basic_info", queryParam) == null) {
                        simpleDao.add("terminal_basic_info", terminal);
                    } else {
                        simpleDao.update("terminal_basic_info", terminal, queryParam);
                    }
                }
                log.info("终端数据同步完成..");
            } else {
                log.warn("没有终端信息需要同步.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("终端信息同步失败：" + e);
        }
    }
}
