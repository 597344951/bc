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
import com.zltel.broadcast.resource.dao.MaterialAlbumMapper;
import com.zltel.broadcast.resource.dao.MaterialMapper;
import com.zltel.broadcast.resource.service.MaterialAlbumService;

@Service
public class MaterialAlbumServiceImpl extends BaseDaoImpl<MaterialAlbum> implements MaterialAlbumService {
    @Resource
    private MaterialAlbumMapper mapper;

    @Resource
    private MaterialMapper materialMapper;

    @Override
    public BaseDao<MaterialAlbum> getInstince() {
        return this.mapper;
    }

    @Override
    public int delete(MaterialAlbum ma) {
        PageRowBounds prb = new PageRowBounds(0, 0);
        Material r = new Material();
        r.setAlbumId(ma.getAlbumId());
        this.materialMapper.query(r, prb);
        if (prb.getTotal() > 0) throw new RRException("此分类下包含数据,无法删除!");

        return this.mapper.deleteByPrimaryKey(ma.getAlbumId());
    }

    @Override
    public List<MaterialAlbum> listMaterialAlbum(MaterialAlbum record) {
        return this.mapper.listMaterialAlbum(record);
    }

}
