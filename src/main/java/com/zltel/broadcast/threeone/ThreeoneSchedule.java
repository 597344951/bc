package com.zltel.broadcast.threeone;

import com.zltel.broadcast.message.bean.Message;
import com.zltel.broadcast.message.service.MessageService;
import com.zltel.broadcast.threeone.schedule.bean.Schedule;
import com.zltel.broadcast.threeone.schedule.service.ScheduleService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 三会一课相关定时任务
 */
@Component
public class ThreeoneSchedule {
    private static final Log log = LogFactory.getLog(ThreeoneSchedule.class);

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private MessageService messageService;

    /**
     * 三会一课日程提醒(每30分钟检查一次)
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void scheduleReport() {
        List<Schedule> schedules = scheduleService.queryEnableSchedule();
        schedules.stream().forEach(schedule -> {
            if (Schedule.STATE_READY == schedule.getState() && System.currentTimeMillis() + 24 * 60 * 60 * 1000 >= schedule.getStartTime().getTime()) {
                //距开始时间小于24小时, 开始通知, 添加通知消息, 发布通知投屏
                log.info("会议: " + schedule.getName() + "即将开始, 开始通知.");
                List<Map<String, Object>> members = scheduleService.queryScheduleMembers(schedule.getId());
                members.stream().forEach(member -> {
                    messageService.addMessage(Message.TYPE_NOTICE, schedule.getName(), schedule.getDescription(), (Integer) member.get("id"), schedule.getId(), "/threeone/schedule/notice/" + schedule.getId());
                });
                scheduleService.updateSchedule(new Schedule(schedule.getId(), Schedule.STATE_REPORTED));
                //通知添加日程人员发布通知
                String url = "/view/publish/new.jsp?title="
                        + URLEncoder.encode(schedule.getName())
                        + "&startStep=2&url="
                        + URLEncoder.encode("[{weburl: \"/threeone/schedule/notice/" + schedule.getId() + "\", playtime: 60}]");
                messageService.addMessage(Message.TYPE_HANDLE_PENDING, schedule.getName(), schedule.getDescription(), schedule.getUserId(), schedule.getId(), url);
            }
        });
    }
}
