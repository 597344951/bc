package com.zltel.broadcast.resource.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.resource.bean.MaterialAlbum;

public interface MaterialAlbumMapper extends BaseDao<MaterialAlbum> {
    int deleteByPrimaryKey(Integer albumId);

    int insert(MaterialAlbum record);

    int insertSelective(MaterialAlbum record);

    MaterialAlbum selectByPrimaryKey(Integer albumId);

    int updateByPrimaryKeySelective(MaterialAlbum record);

    int updateByPrimaryKey(MaterialAlbum record);

    List<MaterialAlbum> listMaterialAlbum(MaterialAlbum record);
}
