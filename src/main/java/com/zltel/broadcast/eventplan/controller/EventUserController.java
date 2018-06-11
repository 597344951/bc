package com.zltel.broadcast.eventplan.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.validator.ValidatorUtils;
import com.zltel.broadcast.eventplan.bean.EventList;
import com.zltel.broadcast.eventplan.bean.EventUser;
import com.zltel.broadcast.eventplan.service.EventListService;
import com.zltel.broadcast.eventplan.service.EventUserService;
@RestController
@RequestMapping("/event/user")
public class EventUserController extends BaseController {
    
    @Resource
    private EventUserService eventUserService;
    
    
    
    @GetMapping("/user-event")
    public R get() {
        
        return R.ok();
    }
    
    
    @PostMapping("/user-event")
    public R save(@RequestBody EventUser eu) {
        eu.setId(null);
        ValidatorUtils.validateEntity(eu);
        eu.setTimeType(0);
        eu.setOrgId(this.getSysUser().getOrgId());
        
        this.eventUserService.saveUserEvent(eu);
        return R.ok();
    }
    @PutMapping("/user-event")
    public R update() {
        return R.ok();
    }
    
    @DeleteMapping("/user-event")
    public R delete() {
        return R.ok();
    }
    
    
}
