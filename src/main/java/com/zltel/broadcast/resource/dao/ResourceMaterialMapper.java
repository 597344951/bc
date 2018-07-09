package com.zltel.broadcast.resource.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.resource.bean.ResourceMaterial;

public interface ResourceMaterialMapper extends BaseDao<ResourceMaterial> {
    int deleteByPrimaryKey(Integer materialId);

    int insert(ResourceMaterial record);

    int insertSelective(ResourceMaterial record);

    ResourceMaterial selectByPrimaryKey(Integer materialId);

    int updateByPrimaryKeySelective(ResourceMaterial record);

    int updateByPrimaryKeyWithBLOBs(ResourceMaterial record);

    int updateByPrimaryKey(ResourceMaterial record);
}