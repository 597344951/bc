package com.zltel.broadcast.area_manage.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.area_manage.bean.MapAreaBasic;
import com.zltel.broadcast.common.support.BaseDao;

public interface MapAreaBasicMapper extends BaseDao<MapAreaBasic> {
    int deleteByPrimaryKey(Integer id);

    int insert(MapAreaBasic record);

    int insertSelective(MapAreaBasic record);

    MapAreaBasic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MapAreaBasic record);

    int updateByPrimaryKeyWithBLOBs(MapAreaBasic record);

    int updateByPrimaryKey(MapAreaBasic record);
    
    /**
     * 查询区域基础信息
     * @param condition
     * @return
     */
    public List<Map<String, Object>> queryMapAreaBasics(Map<String, Object> condition);
}