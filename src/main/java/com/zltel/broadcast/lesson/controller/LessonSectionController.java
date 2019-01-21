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
import com.zltel.broadcast.lesson.bean.LessonSection;
import com.zltel.broadcast.lesson.service.LessonSectionService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lesson/section")
public class LessonSectionController extends BaseController {

    @Resource
    private LessonSectionService sectionService;

    @ApiOperation("保存课程章节")
    @PostMapping("/section")
    public R save(@RequestBody LessonSection record) {
        this.sectionService.insert(record);
        return R.ok();
    }

    @ApiOperation("更新课程章节")
    @PutMapping("/section")
    public R update(@RequestBody LessonSection record) {
        this.sectionService.updateByPrimaryKeySelective(record);
        return R.ok();
    }

    @ApiOperation("删除课程章节")
    @DeleteMapping("/section/{lessonId}")
    public R delete(@PathVariable("lessonId") Integer lessonId) {
        this.sectionService.deleteByPrimaryKey(lessonId);
        return R.ok();
    }

}
