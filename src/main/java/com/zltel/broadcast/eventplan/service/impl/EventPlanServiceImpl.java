package com.zltel.broadcast.eventplan.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.eventplan.bean.CostPlan;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.dao.CostPlanMapper;
import com.zltel.broadcast.eventplan.dao.EventPlanInfoMapper;
import com.zltel.broadcast.eventplan.service.EventPlanService;
import com.zltel.broadcast.eventplan.service.EventPlanVotingService;
import com.zltel.broadcast.message.bean.Message;
import com.zltel.broadcast.message.service.MessageService;

@Service
public class EventPlanServiceImpl extends BaseDaoImpl<EventPlanInfo> implements EventPlanService {


    private static final Logger logout = LoggerFactory.getLogger(EventPlanServiceImpl.class);


    @Resource
    private EventPlanInfoMapper eventPlanInfoMapper;
    @Resource
    private CostPlanMapper costPlanMapper;
    @Resource
    private MessageService messageService;
    @Resource
    private EventPlanVotingService votingService;

    @Override
    public BaseDao<EventPlanInfo> getInstince() {
        return this.eventPlanInfoMapper;
    }


    @Override
    @Transactional
    public void save(EventPlanInfo eventplan) {
        this.eventPlanInfoMapper.insertSelective(eventplan);
        List<CostPlan> cps = eventplan.getCostplans();
        if (!cps.isEmpty()) {
            cps.forEach(e -> {
                try {
                    e.setEventPlanId(eventplan.getEventPlanId());
                    ValidatorUtils.validateEntity(e);
                    this.costPlanMapper.insertSelective(e);
                } catch (RRException rre) {
                    // 填充数据有问题
                    logout.warn("字段 填充不全!{}", rre.getMessage());
                }
            });
        }
    }

    @Override
    public List<EventPlanInfo> queryUnStop(EventPlanInfo eventplan) {
        return this.eventPlanInfoMapper.queryUnStop(eventplan);
    }

    @Override
    public void updateStatus(EventPlanInfo plan) {
        EventPlanInfo epi = new EventPlanInfo();
        epi.setEventPlanId(plan.getEventPlanId());
        epi.setStatus(plan.getStatus());
        this.eventPlanInfoMapper.updateByPrimaryKeySelective(epi);
    }

    @Override
    public void sendVotingToUser(EventPlanInfo plan) {
        // 查询未投票用户
        List<EventPlanVotingItem> datas = this.votingService.queryVotingInfo(plan.getEventPlanId());
        datas.stream().filter(item -> item.getYesOrNo() == null).forEach(item -> {
            this.sendUserVotingMsg(item, plan);
        });
    }

    private void sendUserVotingMsg(EventPlanVotingItem item, EventPlanInfo plan) {
        Message msg = new Message();
        msg.setType(Message.TYPE_NOTICE);
        msg.setAddDate(new Date());
        msg.setContent(plan.getTitle() + "活动投票通知");
        msg.setUpdateDate(new Date());
        msg.setUserId(item.getUserId());
        msg.setTitle("活动投票:" + plan.getTitle());
        msg.setState(Message.STATE_UNPROCESSED);
        msg.setSourceId(plan.getEventPlanId());

        msg.setUrl("/event/plan/view/" + item.getEventPlanId());
        this.messageService.addMessage(msg);
    }


    @Override
    public void pubTaskIdBackFill(Integer eventPlanId, String pubTaskId) {
        EventPlanInfo plan = new EventPlanInfo();
        plan.setEventPlanId(eventPlanId);
        plan.setPubTaskId(pubTaskId);
        this.eventPlanInfoMapper.updateByPrimaryKeySelective(plan);
    }
}
