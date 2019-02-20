package com.zltel.broadcast.threeone.schedule.controller;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.message.service.MessageService;
import com.zltel.broadcast.threeone.schedule.bean.Schedule;
import com.zltel.broadcast.threeone.schedule.service.ScheduleService;
import com.zltel.broadcast.um.bean.OrganizationInformation;
import com.zltel.broadcast.um.service.OrganizationInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/threeone/schedule")
public class ScheduleController extends BaseController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private OrganizationInformationService organizationInformationService;
    @Autowired
    private MessageService messageService;

    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody Schedule schedule) {
        schedule.setOrgId(getSysUser().getOrgId());
        schedule.setUserId(getSysUser().getUserId());
        scheduleService.addSchedule(schedule);
        return R.ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public R update(@RequestBody Schedule schedule) {
        scheduleService.updateSchedule(schedule);
        return R.ok();
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable int id) {
        scheduleService.deleteSchedule(id);
        return R.ok();
    }

    @GetMapping("/")
    @ResponseBody
    public R query(String meetingType) {
        R r = R.ok();
        if("life".equals(meetingType)) {
            r.setData(scheduleService.queryLifeEnableSchedule(getSysUser()));
        } else if("democratic-appraisal".equals(meetingType)) {
            r.setData(scheduleService.queryDemocraticAppraisalEnableSchedule(getSysUser()));
        } else {
            r.setData(scheduleService.queryThreeoneEnableSchedule(getSysUser()));
        }

        return r;
    }

    @PostMapping("/import")
    @ResponseBody
    public R importFromFile(@RequestParam("file") MultipartFile file) {
        return scheduleService.importSchedules(file, getSysUser());
    }

    @GetMapping("/count")
    @ResponseBody
    public R count() {
        try {
            R r = R.ok();
            OrganizationInformation organizationInformation = new OrganizationInformation();
            organizationInformation.setOrgInfoId(getSysUser().getOrgId());
            List<OrganizationInformation> orgInfoSelects = organizationInformationService.queryOrgInfosSelect(organizationInformation);
            if (!orgInfoSelects.isEmpty()) {
                r.put("org", orgInfoSelects.get(0).getOrgInfoName());
            } else {
                r.put("org", "未知组织");
            }
            r.setData(scheduleService.countCompletedSchedule(getSysUser()));

            return r;
        } catch (Exception e) {
            return R.error().setMsg(e.getMessage());
        }

    }

    @GetMapping("/member/org")
    @ResponseBody
    public R orgMembers() {
        R r = R.ok();
        r.setData(scheduleService.queryOrgMembers(getSysUser().getOrgId()));
        return r;
    }

    @GetMapping("/notice/{id}")
    public String notice(Model model, @PathVariable("id") int id) {
        model.addAttribute("schedule", scheduleService.getSchedule(id));
        try {
            messageService.handleMessage(getSysUser(), id);
        } catch (Exception e) {
            //do nothing ...
        }

        return "/html/threeone/notice/notice01";
    }
}
