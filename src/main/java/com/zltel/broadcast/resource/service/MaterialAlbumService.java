package com.zltel.broadcast.resource.service;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.resource.bean.MaterialAlbum;

public interface MaterialAlbumService extends BaseDao<MaterialAlbum> {
    /**
     * 删除资源分类信息
     * 
     * @param ma
     * @return
     */
    int delete(MaterialAlbum ma);
    
    List<MaterialAlbum> listMaterialAlbum(MaterialAlbum record);

}
