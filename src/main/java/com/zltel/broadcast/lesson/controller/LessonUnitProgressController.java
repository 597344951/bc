package com.zltel.broadcast.lesson.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.lesson.bean.LessonUnitProgress;
import com.zltel.broadcast.lesson.service.LessonUnitProgressService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/lesson/progress")
public class LessonUnitProgressController extends BaseController {
    @Resource
    private LessonUnitProgressService progressService;

    @ApiOperation("记录播放进度")
    @PostMapping("/progress")
    public R save(@RequestBody LessonUnitProgress record) {
        if (record == null || record.getLessonUnitId() == null || null == record.getLessonId())
            throw RRException.makeThrow("记录失败");
        SysUser user = this.getSysUser();
        record.setOrgId(user.getOrgId());
        record.setUserId(user.getUserId());

        this.progressService.recordProgress(record);
        return R.ok();
    }



}
