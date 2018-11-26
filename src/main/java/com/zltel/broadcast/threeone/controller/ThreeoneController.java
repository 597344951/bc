package com.zltel.broadcast.threeone.controller;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.threeone.schedule.bean.Schedule;
import com.zltel.broadcast.threeone.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/threeone")
public class ThreeoneController extends BaseController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/{pageNum}/{pageSize}")
    @ResponseBody
    public R query(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r = R.ok();
        r.setData(new PageInfo<Schedule>(scheduleService.queryCompletedSchedule(getSysUser(), pageNum, pageSize)));
        return r;
    }
}
