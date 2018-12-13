package com.zltel.broadcast.lesson.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.lesson.bean.LessonUnitRegistration;
import com.zltel.broadcast.lesson.service.LessonUnitRegistrationService;
import com.zltel.broadcast.um.bean.SysUser;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lesson/regist")
public class LessonUnitRegistController extends BaseController{
    
    @Resource
    private LessonUnitRegistrationService regService;
    
    @ApiOperation("报名课程")
    @GetMapping("/{lessonUnitId}")
    public R regist(@PathVariable("lessonUnitId") Integer lessonUnitId) {
        SysUser user = this.getSysUser();
        LessonUnitRegistration reg = new LessonUnitRegistration();
        reg.setOrgId(user.getOrgId());
        reg.setUserId(user.getUserId());
        reg.setLessonUnitId(lessonUnitId);
        this.regService.registLesson(reg);
        return R.ok();
    }

}
