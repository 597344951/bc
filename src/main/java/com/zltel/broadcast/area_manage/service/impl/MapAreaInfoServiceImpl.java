package com.zltel.broadcast.area_manage.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaLatLng;
import com.zltel.broadcast.area_manage.dao.MapAreaInfoMapper;
import com.zltel.broadcast.area_manage.dao.MapAreaLatLngMapper;
import com.zltel.broadcast.area_manage.service.MapAreaInfoService;
import com.zltel.broadcast.area_manage.util.AreaConflictException;
import com.zltel.broadcast.area_manage.util.LocationJudgment;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.dao.TerminalBasicInfoMapper;

@Service
public class MapAreaInfoServiceImpl extends BaseDaoImpl<MapAreaInfo> implements MapAreaInfoService {
	
    private static MapAreaInfoMapper mapAreaInfoMapper;
	@Resource
    private MapAreaLatLngMapper mapAreaLatLngMapper;
	
    private static TerminalBasicInfoMapper terminalBasicInfoMapper;
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(MapAreaInfoServiceImpl.class);

    @Override
    public BaseDao<MapAreaInfo> getInstince() {
        return MapAreaInfoServiceImpl.mapAreaInfoMapper;
    }
    @Resource
	public void setMapAreaInfoMapper(MapAreaInfoMapper mapAreaInfoMapper) {
    	MapAreaInfoServiceImpl.mapAreaInfoMapper = mapAreaInfoMapper;
	}
    @Resource
	public void setTerminalBasicInfoMapper(TerminalBasicInfoMapper terminalBasicInfoMapper) {
    	MapAreaInfoServiceImpl.terminalBasicInfoMapper = terminalBasicInfoMapper;
	}
	/**
     * 添加地图区域信息
     * @param condition
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public boolean insertMapAreaInfo(String condition) {
    	Map<String, Object> condi = JSON.parseObject(condition);
    	String name = String.valueOf(condi.get("name"));
    	String describes = String.valueOf(condi.get("describes"));
    	Integer type_id = Integer.parseInt(String.valueOf(condi.get("type_id")));
    	Integer area_level = Integer.parseInt(String.valueOf(condi.get("areaLevel")));
    	
    	List<Map<String, Object>> map_areas = (List)condi.get("map_areas");
    	for (Map<String, Object> map_area : map_areas) {
			String shape = String.valueOf(map_area.get("shape"));
			String fill_color = String.valueOf(map_area.get("fill_color"));
			String stroke_color = String.valueOf(map_area.get("stroke_color"));
			
			MapAreaInfo mai = new MapAreaInfo();
			mai.setTypeId(type_id);
			mai.setShape(shape);
			mai.setName(name);
			mai.setDescribes(describes);
			mai.setFillColor(fill_color);
			mai.setLineColor(stroke_color);
			mai.setCreateTime(new Date());
			mai.setAreaLevel(area_level);
			mapAreaInfoMapper.insertSelective(mai);
			
			MapAreaLatLng mall = null;
			BigDecimal radius = null;
			List<Map<String, BigDecimal>> latlngs = (List)map_area.get("latlngs");
			if ("circle".equals(shape)) {
				radius = new BigDecimal(String.valueOf(map_area.get("radius")));
			} 
			for (int i = 0; i < latlngs.size(); i++) {
				Map<String, BigDecimal> latlng = latlngs.get(i);
				mall = new MapAreaLatLng();
				mall.setInfoId(mai.getId());
				mall.setRadius(radius);
				mall.setLat(latlng.get("lat"));
				mall.setLng(latlng.get("lng"));
				mall.setIndex(i + 1);
				mall.setSouthwestLat(latlng.get("southwestLat"));
				mall.setSouthwestLng(latlng.get("southwestLng"));
				mall.setNortheastLat(latlng.get("northeastLat"));
				mall.setNortheastLng(latlng.get("northeastLng"));
				mapAreaLatLngMapper.insertSelective(mall);
			}
			//查询出没有分配区域的设备
			Map<String, Object> con = new HashMap<>();
			con.put("mapAreaIdIsNull", "mapAreaIdIsNull");
			List<TerminalBasicInfo> tbis = terminalBasicInfoMapper.queryTbi_Area(con);
			//为这些设备重新试分配区域
			for (TerminalBasicInfo tbi : tbis) {
				distributionArea(tbi);
			}
		}
    	return true;
    }
    
    /**
     * 分配区域
     * @return
     */
    public static void distributionArea(TerminalBasicInfo tbi) {
    	List<MapAreaInfo> mais = mapAreaInfoMapper.queryMais(null);
		try {
			MapAreaInfo mai = LocationJudgment.isInPolygon(tbi, mais);
			if (null == mai) {
				tbi.setMapAreaId(null);
			} else {
				tbi.setMapAreaId(mai.getId());
			}
			terminalBasicInfoMapper.updateByPrimaryKeySelective(tbi);
		} catch (AreaConflictException e) {	//区域冲突了
			tbi.setMapAreaId(null);
			terminalBasicInfoMapper.updateByPrimaryKeySelective(tbi);
			logger.info(e.getMessage());
		}
    }
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public PageInfo<Map<String, Object>> queryMapAreaInfos(Map<String, Object> condition, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> mais = mapAreaInfoMapper.queryMapAreaInfos(condition);
		PageInfo<Map<String, Object>> maisPageInfo = new PageInfo<>(mais);
		return maisPageInfo;
    }
    
    /**
     * 查询区域坐标信息
     * @param condition
     * @return
     */
    public List<MapAreaLatLng> queryMapAreaLatLngs(Map<String, Object> condition) {
    	return mapAreaLatLngMapper.queryMapAreaLatLngs(condition);
    }
    
    
    
    
	@Override
	public int updateByPrimaryKeyWithBLOBs(MapAreaInfo record) {
		return 0;
	}
}
