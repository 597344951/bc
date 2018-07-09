package com.zltel.broadcast.eventplan.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.bean.EventPlanStatus;
import com.zltel.broadcast.eventplan.service.EventPlanService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/event")
public class EventPlanController extends BaseController {

    @Resource
    private EventPlanService eventPlanService;

    @ApiOperation(value = "保存活动策划策划")
    @PostMapping("/plan")
    public R save(@RequestBody EventPlanInfo eventplan) {
        SysUser user = this.getSysUser();
        ValidatorUtils.validateEntity(eventplan);
        eventplan.setOrgId(user.getOrgId());
        eventplan.setUserId(user.getUserId());
        eventplan.setStatus(EventPlanStatus.STATUS_READY);

        this.eventPlanService.save(eventplan);
        return R.ok();
    }

    @ApiOperation(value = "查询活动信息")
    @PostMapping("/plan/{pageIndex}-{limit}")
    public R list(@RequestBody EventPlanInfo eventplan, @PathVariable("pageIndex") int pageIndex,
            @PathVariable("limit") int limit) {
        Pager pager = new Pager(pageIndex, limit);
        List<EventPlanInfo> datas = this.eventPlanService.query(eventplan, pager);
        return R.ok().setData(datas).setPager(pager);
    }

    @ApiOperation(value = "加载指定活动信息")
    @GetMapping("/plan/{eventPlanId}")
    public R get(@PathVariable("eventPlanId") String eventPlanId) {
        EventPlanInfo eventplan = this.eventPlanService.selectByPrimaryKey(eventPlanId);
        return R.ok().setData(eventplan);
    }


}
