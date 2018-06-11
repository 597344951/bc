package com.zltel.broadcast.eventplan.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.eventplan.bean.EventList;
import com.zltel.broadcast.eventplan.service.EventListService;
import com.zltel.broadcast.um.bean.SysUser;
@RestController
@RequestMapping("/event")
public class EventListController extends BaseController {

    @Resource
    private EventListService eventListService;

    @PostMapping("/list")
    public R eventList(@RequestBody EventList record) {
        SysUser user = this.getSysUser();
        record.setOrgId(user.getOrgId());

        List<EventList> datas = this.eventListService.queryInTime(record);
        return R.ok().setData(datas);
    }
}
