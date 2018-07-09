package com.zltel.broadcast.resource.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.pager.Pager;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.dao.ResourceMaterialMapper;
import com.zltel.broadcast.resource.service.ResourceMaterialService;

@Service
public class ResourceMaterialServiceImpl extends BaseDaoImpl<ResourceMaterial> implements ResourceMaterialService {

    @Resource
    private ResourceMaterialMapper mapper;

    @Override
    public BaseDao<ResourceMaterial> getInstince() {
        return mapper;
    }

    @Override
    public List<ResourceMaterial> query(ResourceMaterial record, Pager pager) {
        return this.mapper.query(record, pager);
    }

    @Override
    public int delete(ResourceMaterial m) {
        return this.mapper.delete(m);
    }



}
