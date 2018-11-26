package com.zltel.broadcast.publish.dao;

import com.zltel.broadcast.publish.bean.Silhouette;
import com.zltel.broadcast.resource.bean.ResourceMaterial;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SilhouetteMapper {
    int deleteByPrimaryKey(Integer silhouetteId);

    int insert(Silhouette record);

    int insertSelective(Silhouette record);

    Silhouette selectByPrimaryKey(Integer silhouetteId);

    int updateByPrimaryKeySelective(Silhouette record);

    int updateByPrimaryKey(Silhouette record);

    List<ResourceMaterial> queryMaterials(@Param("ids") List<Integer> ids);

    List<Silhouette> query(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    int mapInsert(Map<String, Object> record);

    List<Map<String, Object>> queryGallery(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    
    List<Map<String, Object>> queryXjdx(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    Map<String, Object> mapGet(int id);

    List<Map<String, Object>> queryFlashing(@Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
}