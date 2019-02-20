package com.zltel.broadcast.area_manage.dao;

import java.util.List;

import com.zltel.broadcast.area_manage.bean.MapAreaType;
import com.zltel.broadcast.common.support.BaseDao;

public interface MapAreaTypeMapper extends BaseDao<MapAreaType> {
    int deleteByPrimaryKey(Integer id);

    int insert(MapAreaType record);

    int insertSelective(MapAreaType record);

    MapAreaType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MapAreaType record);

    int updateByPrimaryKey(MapAreaType record);
    
    /**
     * 查询区域类型
     * @param mat
     * @return
     */
    public List<MapAreaType> queryMapAreaTypes(MapAreaType mat);
}