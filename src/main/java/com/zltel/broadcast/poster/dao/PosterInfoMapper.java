package com.zltel.broadcast.poster.dao;

import java.util.List;

import com.zltel.broadcast.poster.bean.PosterInfo;

public interface PosterInfoMapper {
    int deleteByPrimaryKey(Integer templateId);

    int insert(PosterInfo record);

    int insertSelective(PosterInfo record);

    PosterInfo selectByPrimaryKey(Integer templateId);

    int updateByPrimaryKeySelective(PosterInfo record);

    int updateByPrimaryKey(PosterInfo record);

    List<PosterInfo> query(PosterInfo posterinfo);
    
}