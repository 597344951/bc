package com.zltel.broadcast.planjoin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.common.validator.group.GroupSave;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.planjoin.bean.ActivityRegistration;
import com.zltel.broadcast.planjoin.bean.ActivitySign;
import com.zltel.broadcast.planjoin.bean.UserPlanJoin;
import com.zltel.broadcast.planjoin.service.PlanJoinService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RequestMapping("/plan/join/")
@RestController
public class PlanJoinController extends BaseController {

    @Resource
    private PlanJoinService planJoinService;

    @ApiOperation("用户相关活动")
    @PostMapping("/relatedplans/{type}/{pageIndex}-{limit}")
    public R relatedPlans(@PathVariable("type") Integer type, @PathVariable("pageIndex") Integer pageIndex,
            @PathVariable("limit") Integer limit, @RequestBody UserPlanJoin upj) {
        SysUser user = getSysUser();
        upj.setOrgId(user.getOrgId());
        upj.setUserId(user.getUserId());

        Pager pager = new Pager(pageIndex, limit);
        List<EventPlanInfo> datas = null;
        if (type == 1) {
            // 可参加
            datas = this.planJoinService.queryRelatedPlan(upj, pager);
        } else if (type == 2) {
            // 已参加
            datas = this.planJoinService.queryJoinedPlan(upj, pager);
        } else if (type == 3) {
            // 已结束
            datas = this.planJoinService.queryEndPlan(upj, pager);
        }
        // 可参加活动, 同一个组织发布， 活动状态为 正在进行之前的活动
        // 已报名， 报名参加的活动， 状态非结束
        // 已结束 ， 报名参加 结束的活动

        return R.ok().setData(datas).setPager(pager);
    }
    
    @ApiOperation("活动报名")
    @PostMapping("/regist")
    public R registPlan(@RequestBody ActivityRegistration ar) {
        ValidatorUtils.validateEntity(ar,GroupSave.class);
        SysUser user = this.getSysUser();
        ar.setUserId(user.getUserId());
        ar.setOrgId(user.getOrgId());
        
        this.planJoinService.registration(ar);
        return R.ok();
    }
    @ApiOperation("活动签到")
    @PostMapping("/sign")
    public R signPlan(@RequestBody ActivitySign as) {
        ValidatorUtils.validateEntity(as,GroupSave.class);
        SysUser user = this.getSysUser();
        as.setUserId(user.getUserId());
        this.planJoinService.signIn(as);
        return R.ok();
    }
}
