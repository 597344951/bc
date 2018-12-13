package com.zltel.broadcast.resource.dao;

import java.util.List;

import com.zltel.broadcast.common.pager.Pager;
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

    void inserts(List<ResourceMaterial> records);

    Long orgUsedStoreSize(Integer orgid);

    List<ResourceMaterial> queryLearnResource(ResourceMaterial record, Pager pager);
}