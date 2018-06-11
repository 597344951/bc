package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.WorkNatureType;
import com.zltel.broadcast.um.dao.WorkNatureTypeMapper;
import com.zltel.broadcast.um.service.WorkNatureTypeService;

@Service
public class WorkNatureTypeServiceImpl extends BaseDaoImpl<WorkNatureType> implements WorkNatureTypeService {
	@Resource
    private WorkNatureTypeMapper workNatureTypeMapper;
	
	@Override
    public BaseDao<WorkNatureType> getInstince() {
        return this.workNatureTypeMapper;
    }
	
	/**
     * 查询工作性质
     * @param workNatureType
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryWorkNatureTypes(WorkNatureType workNatureType) throws Exception {
		List<WorkNatureType> workNatureTypes = workNatureTypeMapper.queryWorkNatureTypes(workNatureType);	//开始查询，没有条件则查询所有基础用户信息
		if (workNatureTypes != null && workNatureTypes.size() > 0) {	//是否查询到数据
			return R.ok().setData(workNatureTypes).setMsg("查询工作性质成功");
		} else {
			return R.ok().setMsg("没有查询到工作性质");
		}
    }
}
