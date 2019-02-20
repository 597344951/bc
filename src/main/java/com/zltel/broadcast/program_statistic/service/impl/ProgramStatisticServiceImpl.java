package com.zltel.broadcast.program_statistic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.program_statistic.bean.BaseProgramInfo;
import com.zltel.broadcast.program_statistic.bean.ProgramStatistic;
import com.zltel.broadcast.program_statistic.dao.ProgramPlaylistMapper;
import com.zltel.broadcast.program_statistic.dao.ProgramStatisticMapper;
import com.zltel.broadcast.program_statistic.service.ProgramStatisticService;

@Service
public class ProgramStatisticServiceImpl implements ProgramStatisticService {

    @Resource
    private ProgramStatisticMapper mapper;

    @Resource
    private ProgramPlaylistMapper programPlayListMapper;

    @Override
    @Transactional
    public void saveStatistic(List<ProgramStatistic> pss) {
        if (pss == null || pss.isEmpty()) return;
        pss.stream().forEach(this::saveStatistic);
        this.saveProgramPlayList(pss);
    }

    private void saveStatistic(ProgramStatistic ps) {
        ps.setId(null);
        this.mapper.insert(ps);
    }

    @Override
    public void saveProgramPlayList(List<ProgramStatistic> list) {
        list.stream().distinct().forEach(this::saveProgramPlayList);
    }

    private void saveProgramPlayList(ProgramStatistic ps) {
        BaseProgramInfo bpi = this.programPlayListMapper.selectByPrimaryKey(ps);
        if (bpi == null) {
            this.programPlayListMapper.insert(ps);
        } else {
            this.programPlayListMapper.updateByPrimaryKeySelective(ps);
        }
    }

}
