package com.zltel.broadcast.lesson.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.lesson.bean.LessonLearnerLimit;
import com.zltel.broadcast.lesson.service.LessonLearnerLimitService;

import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/lesson/learn-limit")
public class LessonLearnerLimitController extends BaseController {
    
    @Resource
    private LessonLearnerLimitService learnLimitService;
    
    @ApiOperation("配置组织限定")
    @PostMapping("/config")
    public R save(@RequestBody List<LessonLearnerLimit> list) {
        learnLimitService.configOrgLimit(list);
        return R.ok();
    }

}
