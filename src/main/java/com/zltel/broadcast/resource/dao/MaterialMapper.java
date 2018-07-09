package com.zltel.broadcast.resource.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.resource.bean.Material;

public interface MaterialMapper extends BaseDao<Material> {
    int deleteByPrimaryKey(Integer materialId);

    int insert(Material record);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Integer materialId);

    int updateByPrimaryKeySelective(Material record);

    int updateByPrimaryKeyWithBLOBs(Material record);

    int updateByPrimaryKey(Material record);

    int delete(Material m);
}
