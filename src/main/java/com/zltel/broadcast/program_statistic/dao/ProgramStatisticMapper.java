package com.zltel.broadcast.program_statistic.dao;

import com.zltel.broadcast.program_statistic.bean.ProgramStatistic;

public interface ProgramStatisticMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProgramStatistic record);

    int insertSelective(ProgramStatistic record);

    ProgramStatistic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProgramStatistic record);

    int updateByPrimaryKey(ProgramStatistic record);
}