package com.zltel.broadcast.resource.service;

import java.util.List;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.resource.bean.ResourceMaterial;

public interface ResourceMaterialService extends BaseDao<ResourceMaterial>{

    List<ResourceMaterial> query(ResourceMaterial record,Pager pager);

    int delete(ResourceMaterial m);
    
    void inserts(List<ResourceMaterial> records);
}
