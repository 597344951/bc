package com.zltel.broadcast.terminal.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.program_statistic.bean.ProgramStatistic;
import com.zltel.broadcast.program_statistic.dao.ProgramStatisticMapper;
import com.zltel.broadcast.terminal.bean.TerminalEcharts;
import com.zltel.broadcast.terminal.dao.TerminalPlaylistMapper;
import com.zltel.broadcast.terminal.service.TerminalPlaylistService;

@Service
public class TerminalPlaylistServiceImpl implements TerminalPlaylistService {
    @Resource
    private ProgramStatisticMapper psm;
    @Resource
    private TerminalPlaylistMapper tpm;

    @Override
    public R userProgram(Integer userId) {
        List<TerminalEcharts> te = tpm.userProgram(userId);
        if (te != null) {
            return R.ok().setData(te).setMsg("查询用户节目成功");
        } else {
            return R.ok().setMsg("没有查询到用户节目信息");
        }

    }

    @Override
    public R statistics(String string) {
        String[] order = string.split("&&");
        Integer userId = Integer.valueOf(order[0]);
        String date = order[1];

        return R.ok();
    }


}
