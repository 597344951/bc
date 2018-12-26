package com.zltel.broadcast.threeone.controller;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.threeone.bean.ThreeoneLearnedWithBLOBs;
import com.zltel.broadcast.threeone.bean.ThreeoneSummaryWithBLOBs;
import com.zltel.broadcast.threeone.schedule.bean.Schedule;
import com.zltel.broadcast.threeone.schedule.service.ScheduleService;
import com.zltel.broadcast.threeone.service.ThreeoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/threeone")
public class ThreeoneController extends BaseController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ThreeoneService threeoneService;

    @Value("${zltel.mediaserve}")
    private String mediaServe;

    @RequestMapping("/")
    public String threeone(Model model, String meetingType) {
        model.addAttribute("mediaServe", mediaServe);
        model.addAttribute("meetingType", meetingType == null ? "" : meetingType);
        return "/view/threeone/index";
    }

    @RequestMapping("/democratic-appraisal")
    public String democraticAppraisal(Model model, String meetingType) {
        model.addAttribute("mediaServe", mediaServe);
        return "/view/democratic-appraisal/index";
    }

    @GetMapping("/{pageNum}/{pageSize}")
    @ResponseBody
    public R query(String meetingType, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r = R.ok();
        if("life".equals(meetingType)) {
            r.setData(new PageInfo<Schedule>(scheduleService.queryLifeCompletedSchedule(getSysUser(), pageNum, pageSize)));
        } else if("democratic-appraisal".equals(meetingType)) {
            r.setData(new PageInfo<Schedule>(scheduleService.queryDemocraticAppraisalCompletedSchedule(getSysUser(), pageNum, pageSize)));
        } else {
            r.setData(new PageInfo<Schedule>(scheduleService.queryThreeoneCompletedSchedule(getSysUser(), pageNum, pageSize)));
        }

        return r;
    }

    @GetMapping("/participated/{pageNum}/{pageSize}")
    @ResponseBody
    public R participatedSchedule(String meetingType, @PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r = R.ok();
        if("life".equals(meetingType)) {
            r.setData(new PageInfo<Map<String, Object>>(scheduleService.queryLifeParticipatedSchedule(getSysUser().getUsername(), pageNum, pageSize)));
        } else if("democratic-appraisal".equals(meetingType)) {
            r.setData(new PageInfo<Map<String, Object>>(scheduleService.queryDemocraticAppraisalParticipatedSchedule(getSysUser().getUsername(), pageNum, pageSize)));
        } else {
            r.setData(new PageInfo<Map<String, Object>>(scheduleService.queryThreeoneParticipatedSchedule(getSysUser().getUsername(), pageNum, pageSize)));
        }

        return r;
    }

    @PostMapping("/summary")
    @ResponseBody
    public R commitSummary(@RequestBody ThreeoneSummaryWithBLOBs summary) {
        threeoneService.addSummary(summary);
        return R.ok();
    }

    @GetMapping("/summary/{scheduleId}")
    @ResponseBody
    public R getSummary(@PathVariable("scheduleId") int scheduleId) {
        R r = R.ok();
        r.setData(threeoneService.querySummary(scheduleId));
        return r;
    }

    @PostMapping("/learned")
    @ResponseBody
    public R commitLearned(@RequestBody ThreeoneLearnedWithBLOBs learned) {
        threeoneService.addLearned(learned);
        return R.ok();
    }

    @GetMapping("/learned/{scheduleId}/{baseUserId}")
    @ResponseBody
    public R getLearned(@PathVariable("scheduleId") int scheduleId, @PathVariable("baseUserId") int baseUserId) {
        R r = R.ok();
        r.setData(threeoneService.queryLearned(scheduleId, baseUserId));
        return r;
    }

    @GetMapping("/participant/{scheduleId}")
    @ResponseBody
    public R participant(@PathVariable("scheduleId") int scheduleId) {
        R r = R.ok();
        r.setData(scheduleService.queryScheduleMembers(scheduleId));
        return r;
    }

    @PostMapping("/participant")
    @ResponseBody
    public R participantSign(@RequestBody List<Map<String, Object>> participant) {
        scheduleService.scheduleSign(participant);
        return R.ok();
    }
}
