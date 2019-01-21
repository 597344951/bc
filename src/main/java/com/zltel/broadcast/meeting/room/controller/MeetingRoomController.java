package com.zltel.broadcast.meeting.room.controller;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.meeting.room.bean.MeetingRoom;
import com.zltel.broadcast.meeting.room.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Controller
@RequestMapping("/meeting/room")
public class MeetingRoomController extends BaseController {
    @Autowired
    private MeetingRoomService meetingRoomService;

    @PostMapping("/add")
    @ResponseBody
    public R add(@RequestBody MeetingRoom room) {
        R r = R.ok();
        room.setOrgId(getSysUser().getOrgId());
        room.setAddDate(new Date(System.currentTimeMillis()));
        meetingRoomService.addMeetingRoom(room);
        return r;
    }

    @PostMapping("/update")
    @ResponseBody
    public R update(@RequestBody MeetingRoom room) {
        R r = R.ok();
        meetingRoomService.updateMeetingRoom(room);
        return r;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public R delete(@PathVariable("id") int id) {
        R r = R.ok();
        meetingRoomService.deleteMeetingRoom(id);
        return r;
    }

    @GetMapping("/list/{pageNum}/{pageSize}")
    @ResponseBody
    public R list(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {
        R r = R.ok();
        r.setData(meetingRoomService.queryOrgMeetingRoom(getSysUser().getOrgId(), pageNum, pageSize));
        return r;
    }

    @GetMapping("/{id}/orders")
    @ResponseBody
    public R orders(@PathVariable("id") int id) {
        R r = R.ok();
        r.setData(meetingRoomService.queryMeetingRoomOrder(id));
        return r;
    }

    @PostMapping("/enabled")
    @ResponseBody
    public R enabled(Long startDate, Long endDate) {
        R r = R.ok();
        r.setData(meetingRoomService.queryEnableMeetingRoom(getSysUser().getOrgId(), new Date(startDate), new Date(endDate)));
        return r;
    }
}
