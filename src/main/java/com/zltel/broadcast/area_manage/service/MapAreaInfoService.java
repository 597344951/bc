package com.zltel.broadcast.area_manage.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaBasic;
import com.zltel.broadcast.area_manage.bean.MapAreaInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaLatLng;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;

public interface MapAreaInfoService {
	int deleteByPrimaryKey(Integer id);

    int insert(MapAreaInfo record);

    int insertSelective(MapAreaInfo record);

    MapAreaInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MapAreaInfo record);

    int updateByPrimaryKeyWithBLOBs(MapAreaInfo record);

    int updateByPrimaryKey(MapAreaInfo record);
    
    /**
     * 查询区域基础信息
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Map<String, Object>> queryMapAreaBasicsPages(Map<String, Object> condition, int pageNum, int pageSize);
    
    /**
     * 查询区域基础信息
     * @param condition
     * @return
     */
    public List<Map<String, Object>> queryMapAreaBasics(Map<String, Object> condition);
    
    /**
     * 修改
     * @param mab
     * @return
     */
    public boolean updateMapAreaBasic(MapAreaBasic mab);
    
    /**
     * 添加地图区域信息
     * @param condition
     * @return
     */
    public boolean insertMapAreaInfo(String condition);
    
    /**
     * 修改地图区域信息
     * @param condition
     * @return
     */
    public boolean updateMapAreaInfo(MapAreaInfo mai);
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public PageInfo<MapAreaInfo> queryMapAreaInfos(Map<String, Object> condition, int pageNum, int pageSize);
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public List<MapAreaInfo> queryMapAreaInfosNoPage(Map<String, Object> condition);
    
    /**
     * 查询区域内的终端信息
     * @param condition
     * @return
     */
    public List<TerminalBasicInfo> queryAreaTerminal(Map<String, Object> condition);
    
    /**
     * 查询区域内的终端信息
     * @param condition
     * @return
     */
    public PageInfo<TerminalBasicInfo> queryAreaTerminalPages(Map<String, Object> condition, int pageNum, int pageSize);
    
    /**
     * 查询区域坐标信息
     * @param condition
     * @return
     */
    public List<MapAreaLatLng> queryMapAreaLatLngs(Map<String, Object> condition);
}
