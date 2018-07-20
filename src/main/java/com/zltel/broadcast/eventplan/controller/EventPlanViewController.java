package com.zltel.broadcast.eventplan.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.eventplan.bean.CostPlan;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItemKey;
import com.zltel.broadcast.eventplan.bean.VotingResult;
import com.zltel.broadcast.eventplan.service.CostPlanService;
import com.zltel.broadcast.eventplan.service.EventPlanService;
import com.zltel.broadcast.eventplan.service.EventPlanVotingService;

@Controller
@RequestMapping("/event/plan")
public class EventPlanViewController extends BaseController {

    @Resource
    private EventPlanService eventPlanService;
    @Resource
    private CostPlanService costPlanService;
    @Resource
    private EventPlanVotingService votingService;


    @GetMapping("/view/{eventPlanId}")
    public ModelAndView view1(@PathVariable("eventPlanId") Integer eventPlanId) {
        EventPlanInfo epi = this.eventPlanService.selectByPrimaryKey(eventPlanId);
        if (null == epi) throw new RRException("找不到计划");
        ModelAndView model = new ModelAndView();
        List<CostPlan> costPlans = this.costPlanService.selectByEventPlanId(epi.getEventPlanId());
        EventPlanVotingItemKey vik = new EventPlanVotingItemKey();
        vik.setEventPlanId(epi.getEventPlanId());
        vik.setUserId(getSysUser().getUserId());
        EventPlanVotingItem vitingItem = this.votingService.selectByPrimaryKey(vik);
        VotingResult vr = this.votingService.votingResult(eventPlanId);

        model.addObject("eventPlan", epi);
        model.addObject("costPlans", costPlans);
        model.addObject("vitingItem", vitingItem);
        model.addObject("votingResult",vr);
        
        model.setViewName("/view/eventplan/plan-view");
        return model;
    }
}
