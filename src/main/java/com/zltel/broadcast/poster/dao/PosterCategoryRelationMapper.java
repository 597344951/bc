package com.zltel.broadcast.poster.dao;

import com.zltel.broadcast.poster.bean.PosterCategoryRelationKey;

public interface PosterCategoryRelationMapper {
    int delete(PosterCategoryRelationKey key);

    int deleteByPrimaryKey(PosterCategoryRelationKey key);

    int insert(PosterCategoryRelationKey record);

    int insertSelective(PosterCategoryRelationKey record);
    
    Integer[] queryCategorys(Integer templateId);
}
