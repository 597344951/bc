package com.zltel.broadcast.program_statistic.dao;

import com.zltel.broadcast.program_statistic.bean.BaseProgramInfo;

public interface ProgramPlaylistMapper {

    int deleteByPrimaryKey(BaseProgramInfo key);

    int insert(BaseProgramInfo record);

    int insertSelective(BaseProgramInfo record);

    BaseProgramInfo selectByPrimaryKey(BaseProgramInfo key);

    int updateByPrimaryKeySelective(BaseProgramInfo record);

    int updateByPrimaryKey(BaseProgramInfo record);
}