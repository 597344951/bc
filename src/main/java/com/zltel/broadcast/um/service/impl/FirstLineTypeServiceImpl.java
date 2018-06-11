package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.FirstLineType;
import com.zltel.broadcast.um.dao.FirstLineTypeMapper;
import com.zltel.broadcast.um.service.FirstLineTypeService;

@Service
public class FirstLineTypeServiceImpl extends BaseDaoImpl<FirstLineType> implements FirstLineTypeService {
	@Resource
    private FirstLineTypeMapper firstLineTypeMapper;
	
	@Override
    public BaseDao<FirstLineType> getInstince() {
        return this.firstLineTypeMapper;
    }
	
	/**
     * 查询一线情况
     * @param firstLineType
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryFirstLineTypes(FirstLineType firstLineType) throws Exception {
		List<FirstLineType> firstLineTypes = firstLineTypeMapper.queryFirstLineTypes(firstLineType);	//开始查询，没有条件则查询所有基础用户信息
		if (firstLineTypes != null && firstLineTypes.size() > 0) {	//是否查询到数据
			return R.ok().setData(firstLineTypes).setMsg("查询一线情况成功");
		} else {
			return R.ok().setMsg("没有查询到一线情况");
		}
    }
}
