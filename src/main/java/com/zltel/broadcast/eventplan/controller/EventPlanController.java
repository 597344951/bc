package com.zltel.broadcast.eventplan.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.eventplan.bean.EventPlanInfo;
import com.zltel.broadcast.eventplan.bean.EventPlanStatus;
import com.zltel.broadcast.eventplan.service.EventPlanService;
import com.zltel.broadcast.um.bean.SysUser;

@RestController
@RequestMapping("/event")
public class EventPlanController extends BaseController{
    
    @Resource
    private EventPlanService eventPlanService;

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
}
