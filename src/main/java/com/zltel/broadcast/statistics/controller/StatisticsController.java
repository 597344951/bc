package com.zltel.broadcast.statistics.controller;

import com.zltel.broadcast.common.controller.BaseController;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.statistics.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/statistics")
public class StatisticsController extends BaseController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping(value = "/partyMember")
    @ResponseBody
    public R partyMember() {
        R r = R.ok();
        r.setData(statisticsService.partyMemberStatistics());
        return r;
    }

    @GetMapping(value = "/partyFeePayment")
    @ResponseBody
    public R partyFeePayment() {
        R r = R.ok();
        r.setData(statisticsService.partyFeePaymentStatistics());
        return r;
    }
}
