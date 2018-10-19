package com.zltel.broadcast.poster.dao;

import java.util.List;

import com.zltel.broadcast.poster.bean.PosterSize;

public interface PosterSizeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PosterSize record);

    int insertSelective(PosterSize record);

    PosterSize selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PosterSize record);

    int updateByPrimaryKey(PosterSize record);

    List<PosterSize> query(PosterSize record);
}
