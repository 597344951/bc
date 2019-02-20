package com.zltel.broadcast.area_manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.area_manage.bean.MapAreaType;
import com.zltel.broadcast.area_manage.dao.MapAreaTypeMapper;
import com.zltel.broadcast.area_manage.service.MapAreaTypeService;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;

@Service
public class MapAreaTypeServiceImpl extends BaseDaoImpl<MapAreaType> implements MapAreaTypeService {
	@Resource
    private MapAreaTypeMapper mapAreaTypeMapper;

    @Override
    public BaseDao<MapAreaType> getInstince() {
        return this.mapAreaTypeMapper;
    }
    
    /**
     * 查询区域类型
     * @param mat
     * @return
     */
    public List<MapAreaType> queryMapAreaTypes(MapAreaType mat) {
    	return mapAreaTypeMapper.queryMapAreaTypes(mat);
    }
}
