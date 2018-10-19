package com.zltel.broadcast.eventplan.timer;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.bean.EventPlanStatus;
import com.zltel.broadcast.eventplan.service.EventPlanService;
import com.zltel.broadcast.eventplan.service.EventPlanVotingService;
import com.zltel.broadcast.example.timer.TestTimer;
import com.zltel.broadcast.um.util.DateUtil;

@Component
public class PlanTimer {
    /** 日志输出对象 **/
    public static final Logger logger = LoggerFactory.getLogger(TestTimer.class);

    // 投票开始前延时
    private static final long VOTING_DELAY = 0;// TimeUnit.DAYS.toMillis(1);
    // 投票时间
    private static final long VOTING_TIME = TimeUnit.DAYS.toMillis(8);

    @Resource
    private EventPlanService eventService;
    @Resource
    private EventPlanVotingService votingService;

    @Scheduled(cron = "0 * * * * ?")
    public void taskProcess() {
        // 查询未结束任务
        List<EventPlanInfo> plans = this.eventService.queryUnStop(null);
        logger.debug("查询 未结束任务,个数: {}", plans.size());
        if (plans.isEmpty()) return;

        // 处理任务
        plans.forEach(this::handlePlan);
    }

    public void handlePlan(EventPlanInfo plan) {

        Integer status = plan.getStatus();
        if (status == EventPlanStatus.STATUS_READY) {
            checkForVoting(plan);
        } else if (status == EventPlanStatus.STATUS_VOTING) {
            checkVotingEnd(plan);
        } else if (status == EventPlanStatus.STATUS_VOTING_PASS) {
            plan.setStatus(EventPlanStatus.STATUS_WAIT_FOR_START);
            this.eventService.updateStatus(plan);
        } else if (status == EventPlanStatus.STATUS_WAIT_FOR_START) {
            checkStart(plan);
        } else if (status == EventPlanStatus.STATUS_PROGRESS) {
            checkStop(plan);
        }
    }

    /** 判断活动是否结束 **/
    private void checkStop(EventPlanInfo plan) {
        Date etime = DateUtil.getDateOfEndTime(plan.getEtime());
        if (System.currentTimeMillis() >= etime.getTime()) {
            // 活动 到达结束开始
            plan.setStatus(EventPlanStatus.STATUS_END);
            this.eventService.updateStatus(plan);
        }
    }

    /** 判断活动是否开始 **/
    private void checkStart(EventPlanInfo plan) {
        Date stime = DateUtil.getDateOfStartTime(plan.getStime());
        if (System.currentTimeMillis() >= stime.getTime()) {
            // 活动 到达时间开始
            plan.setStatus(EventPlanStatus.STATUS_PROGRESS);
            this.eventService.updateStatus(plan);
        }
    }

    /** 延迟一段时间后开始投票, 检测是否开始投票 **/
    private void checkForVoting(EventPlanInfo plan) {
        long dural = System.currentTimeMillis() - plan.getSaveTime().getTime();
        if (dural < VOTING_DELAY) return;
        // 开始投票
        plan.setStatus(EventPlanStatus.STATUS_VOTING);
        this.eventService.updateStatus(plan);
        this.eventService.sendVotingToUser(plan);
    }

    /** 检测 投票是否结束 **/
    private void checkVotingEnd(EventPlanInfo plan) {
        long dural = System.currentTimeMillis() - plan.getSaveTime().getTime();
        if (dural < VOTING_TIME) return;

        // 投票截至, 统计投票状态
        boolean ret = this.votingService.votingPass(plan.getEventPlanId());
        if (ret) {
            // 投票通过
            plan.setStatus(EventPlanStatus.STATUS_VOTING_PASS);
            this.eventService.updateStatus(plan);
        } else {
            // 未通过,结束
            plan.setStatus(EventPlanStatus.STATUS_NOT_PAST);
            this.eventService.updateStatus(plan);
        }
    }

}
