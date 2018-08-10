package com.zltel.broadcast.resource.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageRowBounds;
import com.zltel.broadcast.common.exception.RRException;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.resource.bean.Material;
import com.zltel.broadcast.resource.bean.MaterialAlbum;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import com.zltel.broadcast.resource.dao.MaterialAlbumMapper;
import com.zltel.broadcast.resource.dao.ResourceMaterialMapper;
import com.zltel.broadcast.resource.service.MaterialAlbumService;

@Service
public class MaterialAlbumServiceImpl extends BaseDaoImpl<MaterialAlbum> implements MaterialAlbumService {
    @Resource
    private MaterialAlbumMapper mapper;

    @Resource
    private ResourceMaterialMapper resourceMaterialMapper;

    @Override
    public BaseDao<MaterialAlbum> getInstince() {
        return this.mapper;
    }

    @Override
    public int delete(MaterialAlbum ma) {
        PageRowBounds prb = new PageRowBounds(0, 0);
        ResourceMaterial r = new ResourceMaterial();
        r.setAlbumId(ma.getAlbumId());
        MaterialAlbum dt = this.mapper.selectByPrimaryKey(ma.getAlbumId());
        if (dt == null) throw new RRException("没有找到要删除数据");
        if (dt.getBuiltin()) throw new RRException("无法删除内置节点");
        
        this.resourceMaterialMapper.query(r, prb);
        if (prb.getTotal() > 0) throw new RRException("此分类下包含数据,无法删除!");

        return this.mapper.deleteByPrimaryKey(ma.getAlbumId());
    }

    @Override
    public List<MaterialAlbum> listMaterialAlbum(MaterialAlbum record) {
        return this.mapper.listMaterialAlbum(record);
    }

}
