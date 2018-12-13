package com.zltel.broadcast.learncenter.dao;

import java.util.List;

import com.zltel.broadcast.learncenter.bean.LearnCenterHistory;

public interface LearnCenterHistoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LearnCenterHistory record);

    int insertSelective(LearnCenterHistory record);

    LearnCenterHistory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LearnCenterHistory record);

    int updateByPrimaryKey(LearnCenterHistory record);

    List<LearnCenterHistory> query(LearnCenterHistory record);
    
    int sumScore(LearnCenterHistory record);
}
