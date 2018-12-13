package com.zltel.broadcast.learncenter.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.pager.PagerHelper;
import com.zltel.broadcast.learncenter.bean.LearnCenterHistory;
import com.zltel.broadcast.learncenter.bean.LearningProgress;
import com.zltel.broadcast.learncenter.service.LearnCenterHistoryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/learn-center/")
public class LearnHistoryController extends BaseController {

    @Resource
    private LearnCenterHistoryService historyService;

    @ApiOperation("增加历史纪录")
    @PostMapping("/history")
    public R saveHistory(@RequestBody LearnCenterHistory record) {
        this.historyService.saveHistory(record);
        return R.ok();
    }

    @ApiOperation("学习进度统计")
    @GetMapping("/progress")
    public R getLearningProgress() {
        LearnCenterHistory record = new LearnCenterHistory();
        record.setUserId(this.getSysUser().getUserId());
        LearningProgress lp = this.historyService.learningProcess(record);
        return R.ok().setData(lp);
    }

    @ApiOperation("查询积分获取记录")
    @PostMapping("/history/{pageIndex}-{limit}")
    public R queryHistory(@PathVariable("pageIndex") int pageIndex, @PathVariable("limit") int limit,
            @RequestBody LearnCenterHistory record) {
        Page<?> page = PageHelper.startPage(pageIndex, limit);
        List<LearnCenterHistory> list = this.historyService.query(record);
        Pager pager = PagerHelper.toPager(page);
        return R.ok().setData(list).setPager(pager);
    }


}
