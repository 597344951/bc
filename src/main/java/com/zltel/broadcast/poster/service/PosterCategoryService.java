package com.zltel.broadcast.poster.service;

import java.util.List;

import com.zltel.broadcast.poster.bean.PosterCategory;

public interface PosterCategoryService {
    
    List<PosterCategory> query(PosterCategory record);

    PosterCategory selectByPrimaryKey(Integer categoryId);

    void deleteByPrimaryKey(Integer categoryId);

    int insert(PosterCategory record);

    int insertSelective(PosterCategory record);

    void updateByPrimaryKey(PosterCategory record);

    void updateByPrimaryKeySelective(PosterCategory record);

}
