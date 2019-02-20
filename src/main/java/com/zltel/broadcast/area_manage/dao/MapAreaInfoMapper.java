package com.zltel.broadcast.area_manage.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.area_manage.bean.MapAreaInfo;
import com.zltel.broadcast.common.support.BaseDao;

public interface MapAreaInfoMapper extends BaseDao<MapAreaInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(MapAreaInfo record);

    int insertSelective(MapAreaInfo record);

    MapAreaInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MapAreaInfo record);

    int updateByPrimaryKeyWithBLOBs(MapAreaInfo record);

    int updateByPrimaryKey(MapAreaInfo record);
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public List<Map<String, Object>> queryMapAreaInfos(Map<String, Object> condition);
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public List<MapAreaInfo> queryMais(Map<String, Object> condition);
}