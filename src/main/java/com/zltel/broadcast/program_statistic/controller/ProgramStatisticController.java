package com.zltel.broadcast.program_statistic.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.program_statistic.bean.ProgramStatistic;
import com.zltel.broadcast.program_statistic.service.ProgramStatisticService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/program-statistic")
public class ProgramStatisticController extends BaseController {

    @Resource
    private ProgramStatisticService psService;
    
    @PostMapping("/upload")
    @ApiOperation("上传节目统计数据")
    public R uploadStatistic(@RequestBody List<ProgramStatistic> pss) {
        this.psService.saveStatistic(pss);
        return R.ok();
    }

}
