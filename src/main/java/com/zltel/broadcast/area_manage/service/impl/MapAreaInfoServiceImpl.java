package com.zltel.broadcast.area_manage.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaBasic;
import com.zltel.broadcast.area_manage.bean.MapAreaInfo;
import com.zltel.broadcast.area_manage.bean.MapAreaLatLng;
import com.zltel.broadcast.area_manage.dao.MapAreaBasicMapper;
import com.zltel.broadcast.area_manage.dao.MapAreaInfoMapper;
import com.zltel.broadcast.area_manage.dao.MapAreaLatLngMapper;
import com.zltel.broadcast.area_manage.service.MapAreaInfoService;
import com.zltel.broadcast.area_manage.util.AreaConflictException;
import com.zltel.broadcast.area_manage.util.LocationJudgment;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.terminal.bean.TerminalBasicInfo;
import com.zltel.broadcast.terminal.dao.TerminalBasicInfoMapper;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class MapAreaInfoServiceImpl extends BaseDaoImpl<MapAreaInfo> implements MapAreaInfoService {
	
	//默认10个线程，应该够用
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
    private static MapAreaInfoMapper mapAreaInfoMapper;
	@Resource
    private MapAreaLatLngMapper mapAreaLatLngMapper;
	@Resource
    private MapAreaBasicMapper mapAreaBasicMapper;
	
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
     * 查询区域基础信息
     * @param condition
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<Map<String, Object>> queryMapAreaBasicsPages(Map<String, Object> condition, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> mabs = mapAreaBasicMapper.queryMapAreaBasics(condition);
		PageInfo<Map<String, Object>> mabsPageInfo = new PageInfo<>(mabs);
		if (mabs != null && mabs.size() > 0) {
			for (Map<String, Object> mab : mabs) {
				mab.put("createTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD, mab.get("createTime") == null ||
					mab.get("createTime") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, mab.get("createTime").toString())));
			}
		}
		return mabsPageInfo;
    }
    
    /**
     * 查询区域基础信息
     * @param condition
     * @return
     */
    public List<Map<String, Object>> queryMapAreaBasics(Map<String, Object> condition) {
    	return mapAreaBasicMapper.queryMapAreaBasics(condition);
    }
    
    /**
     * 修改
     * @param mab
     * @return
     */
    public boolean updateMapAreaBasic(MapAreaBasic mab) {
    	mab.setCreateTime(null);
    	mab.setDescribes(mab.getDescribes() == null ? "" : mab.getDescribes());
    	mapAreaBasicMapper.updateByPrimaryKeySelective(mab);
    	return true;
    }
    
    /**
     * 修改地图区域信息
     * @param condition
     * @return
     */
    public boolean updateMapAreaInfo(MapAreaInfo mai) {
    	mapAreaInfoMapper.updateByPrimaryKeySelective(mai);
    	executor.submit(() -> {
    		//查询全部设备，虽然部分设备已经分配区域，但可能会被优先级更高的区域代替
			List<TerminalBasicInfo> tbis = terminalBasicInfoMapper.queryTbi_Area(null);
			if (tbis != null && tbis.size() > 0) {
				//为这些设备重新试分配区域
				for (TerminalBasicInfo tbi : tbis) {
					distributionArea(tbi);
				} 
			}
    	});
    	return true;
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
    	String describes = condi.get("describes") == null ? null : String.valueOf(condi.get("describes"));
    	BigDecimal discount = new BigDecimal(String.valueOf(condi.get("discount")));
    	Integer type_id = Integer.parseInt(String.valueOf(condi.get("type_id")));
    	Integer area_level = Integer.parseInt(String.valueOf(condi.get("areaLevel")));
    	
    	MapAreaBasic mab = new MapAreaBasic();
    	mab.setTypeId(type_id);
    	mab.setName(name);
    	mab.setDescribes(describes);
    	mab.setCreateTime(new Date());
    	mapAreaBasicMapper.insertSelective(mab);
    	
    	List<Map<String, Object>> map_areas = (List)condi.get("map_areas");
    	for (Map<String, Object> map_area : map_areas) {
			String shape = String.valueOf(map_area.get("shape"));
			String fill_color = String.valueOf(map_area.get("fill_color"));
			String stroke_color = String.valueOf(map_area.get("stroke_color"));
			
			MapAreaInfo mai = new MapAreaInfo();
			mai.setBasicId(mab.getId());
			mai.setShape(shape);
			mai.setFillColor(fill_color);
			mai.setLineColor(stroke_color);
			mai.setAreaLevel(area_level);
			mai.setDiscount(discount);
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
			//查询全部设备，虽然部分设备已经分配区域，但可能会被优先级更高的区域代替
			List<TerminalBasicInfo> tbis = terminalBasicInfoMapper.queryTbi_Area(null);
			if (tbis != null && tbis.size() > 0) {
				//为这些设备重新试分配区域
				for (TerminalBasicInfo tbi : tbis) {
					distributionArea(tbi);
				} 
			}
		}
    	return true;
    }
    
    /**
     * 分配区域</br>
     * 未知的终端数量和区域数量，处理过程可能会非常的长</br>
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
			terminalBasicInfoMapper.updateByPrimaryKey(tbi);
		} catch (AreaConflictException e) {	//区域冲突了
			tbi.setMapAreaId(null);
			terminalBasicInfoMapper.updateByPrimaryKey(tbi);
			logger.info(e.getMessage());
		}
    }
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public PageInfo<MapAreaInfo> queryMapAreaInfos(Map<String, Object> condition, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<MapAreaInfo> mais = mapAreaInfoMapper.queryMais(condition);
		PageInfo<MapAreaInfo> maisPageInfo = new PageInfo<>(mais);
		return maisPageInfo;
    }
    
    /**
     * 查询区域信息
     * @param mai
     * @return
     */
    public List<MapAreaInfo> queryMapAreaInfosNoPage(Map<String, Object> condition) {
    	return mapAreaInfoMapper.queryMais(condition);
    }
    
    /**
     * 查询区域内的终端信息
     * @param condition
     * @return
     */
    public List<TerminalBasicInfo> queryAreaTerminal(Map<String, Object> condition) {
    	return terminalBasicInfoMapper.queryTbi_Area(condition);
    }
    
    /**
     * 查询区域内的终端信息
     * @param condition
     * @return
     */
    public PageInfo<TerminalBasicInfo> queryAreaTerminalPages(Map<String, Object> condition, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<TerminalBasicInfo> mais = terminalBasicInfoMapper.queryTbi_Area(condition);
		PageInfo<TerminalBasicInfo> maisPageInfo = new PageInfo<>(mais);
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
