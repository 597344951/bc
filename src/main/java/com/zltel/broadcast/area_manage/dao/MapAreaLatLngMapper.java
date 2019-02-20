package com.zltel.broadcast.area_manage.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.area_manage.bean.MapAreaLatLng;
import com.zltel.broadcast.common.support.BaseDao;

public interface MapAreaLatLngMapper extends BaseDao<MapAreaLatLng> {
    int deleteByPrimaryKey(Integer id);

    int insert(MapAreaLatLng record);

    int insertSelective(MapAreaLatLng record);

    MapAreaLatLng selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MapAreaLatLng record);

    int updateByPrimaryKey(MapAreaLatLng record);
    
    /**
     * 查询区域坐标信息
     * @param condition
     * @return
     */
    public List<MapAreaLatLng> queryMapAreaLatLngs(Map<String, Object> condition);
}