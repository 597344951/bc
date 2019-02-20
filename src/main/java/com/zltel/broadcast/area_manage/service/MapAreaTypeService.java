package com.zltel.broadcast.area_manage.service;

import java.util.List;

import com.zltel.broadcast.area_manage.bean.MapAreaType;

public interface MapAreaTypeService {
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
