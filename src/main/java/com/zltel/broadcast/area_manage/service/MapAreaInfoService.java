package com.zltel.broadcast.area_manage.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaLatLng;

public interface MapAreaInfoService {
	int deleteByPrimaryKey(Integer id);

    int insert(MapAreaInfo record);

    int insertSelective(MapAreaInfo record);

    MapAreaInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MapAreaInfo record);

    int updateByPrimaryKeyWithBLOBs(MapAreaInfo record);

    int updateByPrimaryKey(MapAreaInfo record);
    
    /**
     * 添加地图区域信息
     * @param condition
     * @return
     */
    public boolean insertMapAreaInfo(String condition);
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public PageInfo<Map<String, Object>> queryMapAreaInfos(Map<String, Object> condition, int pageNum, int pageSize);
    
    /**
     * 查询区域坐标信息
     * @param condition
     * @return
     */
    public List<MapAreaLatLng> queryMapAreaLatLngs(Map<String, Object> condition);
}
