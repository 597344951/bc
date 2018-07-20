package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.IntegralChangeType;
import com.zltel.broadcast.um.dao.IntegralChangeTypeMapper;
import com.zltel.broadcast.um.service.IntegralChangeTypeService;

@Service
public class IntegralChangeTypeServiceImpl extends BaseDaoImpl<IntegralChangeType> implements IntegralChangeTypeService {
	@Resource
    private IntegralChangeTypeMapper integralChangeTypeMapper;
	@Override
    public BaseDao<IntegralChangeType> getInstince() {
        return this.integralChangeTypeMapper;
    }
	
	
	/**
     * 查询分值改变类型
     * @param conditions
     * @return
     */
    public R queryICT_ChangeType(IntegralChangeType ict) {
    	List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT_ChangeType(ict);
    	if (icts != null && icts.size() > 0) {
    		return R.ok().setData(icts);
    	} else {
    		return R.ok().setMsg("么有查询到分值类型");
    	}
    }
    
    /**
     * 查询分值变更分类
     * @param conditions
     * @return
     */
    public R queryICT(IntegralChangeType ict) {
    	List<IntegralChangeType> icts = integralChangeTypeMapper.queryICT(ict);
    	if (icts != null && icts.size() > 0) {
    		return R.ok().setData(icts);
    	} else {
    		return R.ok().setMsg("么有查询到分值变更分类");
    	}
    }
}
