package com.zltel.broadcast.poster.dao;

import com.zltel.broadcast.poster.bean.PosterLayouts;

public interface PosterLayoutsMapper {
    int deleteByPrimaryKey(Integer templateId);

    int insert(PosterLayouts record);

    int insertSelective(PosterLayouts record);

    PosterLayouts selectByPrimaryKey(Integer templateId);

    int updateByPrimaryKeySelective(PosterLayouts record);

    int updateByPrimaryKeyWithBLOBs(PosterLayouts record);
}