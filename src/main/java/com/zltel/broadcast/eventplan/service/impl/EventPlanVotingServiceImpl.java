package com.zltel.broadcast.eventplan.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.bean.EventPlanStatus;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItemKey;
import com.zltel.broadcast.eventplan.bean.VotingResult;
import com.zltel.broadcast.eventplan.dao.EventPlanInfoMapper;
import com.zltel.broadcast.eventplan.dao.EventPlanVotingItemMapper;
import com.zltel.broadcast.eventplan.service.EventPlanVotingService;
import com.zltel.broadcast.message.bean.Message;
import com.zltel.broadcast.message.service.MessageService;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;
import com.zltel.broadcast.um.bean.SysUser;
import com.zltel.broadcast.um.service.PartyIntegralRecordService;
import com.zltel.broadcast.um.service.PartyIntegralRecordService.IcChangeType;
import com.zltel.broadcast.um.service.PartyIntegralRecordService.IcType;

@Service
public class EventPlanVotingServiceImpl implements EventPlanVotingService {

    @Resource
    private EventPlanVotingItemMapper votingMapper;
    @Resource
    private EventPlanInfoMapper planMapper;
    @Resource
    private MessageService messageService;
    @Resource
    private PartyIntegralRecordService partyIntegralRecordService;

    @Override
    public int deleteByPrimaryKey(EventPlanVotingItemKey key) {
        return this.votingMapper.deleteByPrimaryKey(key);
    }

    @Override
    public int insert(EventPlanVotingItem record) {
        return this.votingMapper.insert(record);
    }

    @Override
    public int insertSelective(EventPlanVotingItem record) {
        return this.votingMapper.insertSelective(record);
    }

    @Override
    public EventPlanVotingItem selectByPrimaryKey(EventPlanVotingItemKey key) {
        return this.votingMapper.selectByPrimaryKey(key);
    }

    @Override
    public int updateByPrimaryKeySelective(EventPlanVotingItem record) {
        return this.votingMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(EventPlanVotingItem record) {
        return this.votingMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<EventPlanVotingItem> query(EventPlanVotingItem record) {
        return this.votingMapper.query(record);
    }

    @Override
    public int delete(EventPlanVotingItem record) {
        return this.votingMapper.delete(record);
    }

    @Override
    public List<EventPlanVotingItem> queryVotingInfo(Integer eventPlanId) {
        return this.votingMapper.queryVotingInfo(eventPlanId);
    }

    @Override
    public boolean votingPass(Integer eventPlanId) {
        boolean ret = false;
        VotingResult vr = this.votingResult(eventPlanId);

        if (vr.getTotal() == 0) return ret;

        float per = vr.getNoCount() * 100F / vr.getTotal();
        return per < 50;
    }

    @Override
    public VotingResult votingResult(Integer eventPlanId) {
        List<EventPlanVotingItem> datas = this.queryVotingInfo(eventPlanId);
        VotingResult vr = new VotingResult();
        vr.setTotal(datas.size());
        Long noCount = datas.stream().map(EventPlanVotingItem::getYesOrNo)
                .filter(it -> it != null && it == EventPlanVotingItem.NO).count();
        Long yesCount = datas.stream().map(EventPlanVotingItem::getYesOrNo)
                .filter(it -> it != null && it == EventPlanVotingItem.YES).count();
        vr.setNoCount(noCount.intValue());
        vr.setYesCount(yesCount.intValue());
        vr.setDatas(datas);
        return vr;
    }

    @Override
    public void voting(EventPlanVotingItem record) {
        EventPlanInfo plan = this.planMapper.selectByPrimaryKey(record.getEventPlanId());
        if (null == plan) {
            this.clearNoticeMsg(record);
            throw RRException.makeThrow("没有找到投票活动");
        }
        if (plan.getStatus() != EventPlanStatus.STATUS_VOTING) RRException.makeThrow("当前活动投票未开始或已截止");
        if (this.votingMapper.updateByPrimaryKey(record) == 0) {
            this.votingMapper.insert(record);
            // 计算积分
            PartyIntegralRecord pir = new PartyIntegralRecord();
            pir.setActivityId(plan.getEventPlanId());
            pir.setPartyId(record.getUserId());
            pir.setOrgId(plan.getOrgId());
            pir.setIsMerge(1);
            this.partyIntegralRecordService.automaticIntegralRecord(pir, IcType.ACTIVE, IcChangeType.ADD);
        }

        this.clearNoticeMsg(record);

    }

    public void clearNoticeMsg(EventPlanVotingItem record) {
        // 处理 关于投票的消息
        Message msg = new Message();
        msg.setUpdateDate(new Date());
        SysUser user = new SysUser();
        user.setUserId(record.getUserId());
        messageService.handleMessage(user, record.getEventPlanId());
    }


}
