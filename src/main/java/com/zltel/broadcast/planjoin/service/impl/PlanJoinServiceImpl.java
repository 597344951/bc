package com.zltel.broadcast.planjoin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.bean.EventPlanStatus;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItemKey;
import com.zltel.broadcast.eventplan.dao.EventPlanInfoMapper;
import com.zltel.broadcast.eventplan.dao.EventPlanVotingItemMapper;
import com.zltel.broadcast.planjoin.bean.ActivityRegistration;
import com.zltel.broadcast.planjoin.bean.ActivitySign;
import com.zltel.broadcast.planjoin.bean.UserPlanJoin;
import com.zltel.broadcast.planjoin.dao.ActivityRegistrationMapper;
import com.zltel.broadcast.planjoin.dao.ActivitySignMapper;
import com.zltel.broadcast.planjoin.service.PlanJoinService;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;
import com.zltel.broadcast.um.service.PartyIntegralRecordService;
import com.zltel.broadcast.um.service.PartyIntegralRecordService.IcChangeType;
import com.zltel.broadcast.um.service.PartyIntegralRecordService.IcType;

@Service
public class PlanJoinServiceImpl implements PlanJoinService {
    @Resource
    private ActivityRegistrationMapper registraionMapper;
    @Resource
    private ActivitySignMapper signMapper;
    @Resource
    private EventPlanInfoMapper planMapper;

    @Resource
    private EventPlanVotingItemMapper votingMapper;

    @Resource
    private PartyIntegralRecordService partyIntegralRecordService;

    @Override
    public void registration(ActivityRegistration record) {
        EventPlanInfo planInfo = checkEventPlan(record.getEventPlanId(), EventPlanStatus.STATUS_WAIT_FOR_START);

        // 查询
        ActivityRegistration rd = new ActivityRegistration();
        rd.setEventPlanId(record.getEventPlanId());
        rd.setUserId(record.getUserId());
        List<ActivityRegistration> list = this.registraionMapper.query(rd, Pager.DEFAULT_PAGER);
        if (list.isEmpty()) {
            this.registraionMapper.insert(record);
            // 计算积分
            PartyIntegralRecord pir = new PartyIntegralRecord();
            pir.setActivityId(planInfo.getEventPlanId());
            pir.setPartyId(record.getUserId());
            pir.setOrgId(planInfo.getOrgId());
            pir.setIsMerge(1);
            this.partyIntegralRecordService.automaticIntegralRecord(pir, IcType.ACTIVE, IcChangeType.ADD);
        } else {
            RRException.makeThrow("活动已报名");
        }
    }

    /** 检查活动计划 **/
    private EventPlanInfo checkEventPlan(Integer eventPlanId, Integer needStatus) {
        EventPlanInfo planInfo = this.planMapper.selectByPrimaryKey(eventPlanId);
        if (planInfo == null) throw new RRException("报名活动不存在!");
        if (planInfo.getStatus() != needStatus) {
            String emsg = "";
            if (needStatus == EventPlanStatus.STATUS_WAIT_FOR_START) {
                emsg = "活动不可报名";
            } else if (needStatus == EventPlanStatus.STATUS_PROGRESS) {
                emsg = "活动不可签到";
            }
            throw new RRException(emsg);
        }
        return planInfo;
    }

    @Override
    public void signIn(ActivitySign sign) {
        EventPlanInfo planInfo = checkEventPlan(sign.getEventPlanId(), EventPlanStatus.STATUS_PROGRESS);
        ActivitySign rd = new ActivitySign();
        rd.setEventPlanId(sign.getEventPlanId());
        rd.setUserId(sign.getUserId());
        List<ActivitySign> list = this.signMapper.query(rd, Pager.DEFAULT_PAGER);
        if (list.isEmpty()) {
            this.signMapper.insert(sign);
            // 计算积分
            PartyIntegralRecord pir = new PartyIntegralRecord();
            pir.setActivityId(planInfo.getEventPlanId());
            pir.setPartyId(sign.getUserId());
            pir.setOrgId(planInfo.getOrgId());
            pir.setIsMerge(1);
            this.partyIntegralRecordService.automaticIntegralRecord(pir, IcType.ACTIVE, IcChangeType.ADD);
        } else {
            RRException.makeThrow("活动已签到");
        }
    }

    private List<EventPlanInfo> loadEventPlanInfoByIds(List<Integer> ids, Integer userid) {
        List<EventPlanInfo> plans = new ArrayList<>();
        ids.stream().forEach(eventPlanId -> {
            EventPlanInfo plan = this.planMapper.selectByPrimaryKey(eventPlanId);
            // 投票信息
            EventPlanVotingItem userVoting =
                    this.votingMapper.selectByPrimaryKey(new EventPlanVotingItemKey(eventPlanId, userid));
            plan.setUserVoting(userVoting);
            // 报名信息
            ActivityRegistration ar = new ActivityRegistration(eventPlanId, userid);
            List<ActivityRegistration> regs = this.registraionMapper.query(ar, Pager.DEFAULT_PAGER);
            if (!regs.isEmpty()) plan.setUserRegist(regs.get(0));
            // 签到信息
            ActivitySign as = new ActivitySign(eventPlanId, userid);
            List<ActivitySign> ass = this.signMapper.query(as, Pager.DEFAULT_PAGER);
            if (!ass.isEmpty()) plan.setUserSign(ass.get(0));
            plans.add(plan);
        });

        return plans;
    }

    @Override
    public List<EventPlanInfo> queryRelatedPlan(UserPlanJoin upj, Pager pager) {

        List<Integer> ids = this.registraionMapper.queryCanJoinEventPlanId(upj, pager);

        return loadEventPlanInfoByIds(ids, upj.getUserId());
    }

    @Override
    public List<EventPlanInfo> queryJoinedPlan(UserPlanJoin upj, Pager pager) {
        List<Integer> ids = this.registraionMapper.queryRegistedEventPlanId(upj, pager);

        return loadEventPlanInfoByIds(ids, upj.getUserId());
    }

    @Override
    public List<EventPlanInfo> queryEndPlan(UserPlanJoin upj, Pager pager) {
        List<Integer> ids = this.registraionMapper.queryRegistStopedEventPlanId(upj, pager);

        return loadEventPlanInfoByIds(ids, upj.getUserId());
    }

}
