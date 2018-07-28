package com.zltel.broadcast.publish.dao;

import com.zltel.broadcast.publish.bean.Silhouette;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SilhouetteMapper {
    int deleteByPrimaryKey(Integer silhouetteId);

    int insert(Silhouette record);

    int insertSelective(Silhouette record);

    Silhouette selectByPrimaryKey(Integer silhouetteId);

    int updateByPrimaryKeySelective(Silhouette record);

    int updateByPrimaryKey(Silhouette record);

    List<ResourceMaterial> queryMaterials(@Param("ids") List<Integer> ids);

    List<Silhouette> query(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}