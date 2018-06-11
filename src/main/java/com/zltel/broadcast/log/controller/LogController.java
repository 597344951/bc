package com.zltel.broadcast.log.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.log.bean.Log;
import com.zltel.broadcast.log.bean.LogType;
import com.zltel.broadcast.log.service.LogService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private LogService logService;

    @ApiOperation(value = "获取日志分类")
    @GetMapping("/type")
    public R queryLogType() {
        List<LogType> lts = logService.queryLogType(null);
        return R.ok().setData(lts);
    }

    @ApiOperation(value = "查询日志信息")
    @PostMapping("/log/{start}-{limit}")
    public R queryLog(@RequestBody Log log, @PathVariable("start") Integer start,
            @PathVariable("limit") Integer limit) {
        PageRowBounds prb = new PageRowBounds(start, limit);
        List<Log> logs = this.logService.queryLog(log, prb);
        return R.ok().setData(logs).setPager(prb);
    }


}
