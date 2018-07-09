package com.zltel.broadcast.eventplan.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.eventplan.bean.EventPlanVotingItem;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/event")
public class EventPlanVotingController extends BaseController {

    @ApiOperation(value = "加载指定活动计划投票信息")
    @GetMapping("/voting/{eventPlanId}")
    public R get() {

        return R.ok();
    }

    @ApiOperation(value = "更新投票信息")
    @PutMapping("/voting")
    public R save(@RequestBody List<EventPlanVotingItem> records) {
        return R.ok();
    }
}
