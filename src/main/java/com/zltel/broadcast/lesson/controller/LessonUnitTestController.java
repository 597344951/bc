package com.zltel.broadcast.lesson.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.lesson.bean.LessonUnitTest;
import com.zltel.broadcast.lesson.service.LessonUnitTestService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lesson/test")
public class LessonUnitTestController extends BaseController {

    @Resource
    private LessonUnitTestService testService;

    @ApiOperation("新增考核方式")
    @PostMapping("/test")
    public R save(@RequestBody LessonUnitTest record) {
        this.testService.insert(record);
        return R.ok();
    }

    @ApiOperation("更新考核方式")
    @PutMapping("/test")
    public R update(@RequestBody LessonUnitTest record) {
        this.testService.updateByPrimaryKeySelective(record);
        return R.ok();
    }

    @ApiOperation("删除考核方式")
    @DeleteMapping("/test/{testId}")
    public R delete(@PathVariable("testId") Integer testId) {
        this.testService.deleteByPrimaryKey(testId);
        return R.ok();
    }
}
