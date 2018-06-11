package com.zltel.broadcast.eventplan.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.eventplan.bean.CostPlan;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.dao.CostPlanMapper;
import com.zltel.broadcast.eventplan.dao.EventPlanInfoMapper;
import com.zltel.broadcast.eventplan.service.EventPlanService;

@Service
public class EventPlanServiceImpl implements EventPlanService {


    private static final Logger logout = LoggerFactory.getLogger(EventPlanServiceImpl.class);


    @Resource
    private EventPlanInfoMapper eventPlanInfoMapper;

    @Resource
    private CostPlanMapper costPlanMapper;

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

}
