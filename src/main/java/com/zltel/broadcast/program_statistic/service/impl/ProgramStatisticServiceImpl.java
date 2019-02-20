package com.zltel.broadcast.program_statistic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.program_statistic.bean.ProgramStatistic;
import com.zltel.broadcast.program_statistic.dao.ProgramStatisticMapper;
import com.zltel.broadcast.program_statistic.service.ProgramStatisticService;

@Service
public class ProgramStatisticServiceImpl implements ProgramStatisticService {

    @Resource
    private ProgramStatisticMapper mapper;

    @Override
    @Transactional
    public void saveStatistic(List<ProgramStatistic> pss) {
        if (pss == null || pss.isEmpty()) return;
        pss.stream().forEach(this::saveStatistic);
    }
    
    private void saveStatistic(ProgramStatistic ps) {
        ps.setId(null);
        this.mapper.insert(ps);
    }

}
