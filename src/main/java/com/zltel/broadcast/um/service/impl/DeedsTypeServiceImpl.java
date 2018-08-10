package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.DeedsType;
import com.zltel.broadcast.um.dao.DeedsTypeMapper;
import com.zltel.broadcast.um.service.DeedsTypeService;

@Service
public class DeedsTypeServiceImpl extends BaseDaoImpl<DeedsType> implements DeedsTypeService {
	@Resource
    private DeedsTypeMapper deedsTypeMapper;
	@Override
    public BaseDao<DeedsType> getInstince() {
        return this.deedsTypeMapper;
    }
	
	/**
     * 查询用户事迹类型
     * @param dts
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryDeedsTypes(DeedsType dt) {
		List<DeedsType> dts = deedsTypeMapper.queryDeedsTypes(dt);	
		if (dts != null && dts.size() > 0) {	
			return R.ok().setData(dts).setMsg("查询成功");
		} else {
			return R.ok().setMsg("没有查询到信息");
		}
    }
}
