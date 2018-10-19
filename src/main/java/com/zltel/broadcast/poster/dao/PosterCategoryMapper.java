package com.zltel.broadcast.poster.dao;

import java.util.List;

import com.zltel.broadcast.poster.bean.PosterCategory;

public interface PosterCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(PosterCategory record);

    int insertSelective(PosterCategory record);

    PosterCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(PosterCategory record);

    int updateByPrimaryKey(PosterCategory record);
    
    List<PosterCategory> query(PosterCategory record);
}