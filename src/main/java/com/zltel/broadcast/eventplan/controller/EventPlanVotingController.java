package com.zltel.broadcast.eventplan.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;
import com.zltel.broadcast.eventplan.bean.VotingResult;
import com.zltel.broadcast.eventplan.service.EventPlanService;
import com.zltel.broadcast.eventplan.service.EventPlanVotingService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/event")
public class EventPlanVotingController extends BaseController {

    @Resource
    private EventPlanVotingService votingService;
    @Resource
    private EventPlanService planService;

    @ApiOperation(value = "加载指定活动计划投票信息")
    @GetMapping("/voting/{eventPlanId}")
    public R get(@PathVariable("eventPlanId") Integer eventPlanId) {
        VotingResult vr = this.votingService.votingResult(eventPlanId);
        return R.ok().setData(vr);
    }

    @ApiOperation(value = "投票信息")
    @PostMapping("/voting")
    public R save(@RequestBody EventPlanVotingItem record) {
        SysUser user = this.getSysUser();
        record.setUserId(user.getUserId());
        ValidatorUtils.validateEntity(record);
        this.votingService.voting(record);
        return R.ok();
    }
}
