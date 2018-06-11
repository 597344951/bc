package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.EducationLevel;
import com.zltel.broadcast.um.dao.EducationLevelMapper;
import com.zltel.broadcast.um.service.EducationLevelService;

@Service
public class EducationLevelServiceImpl extends BaseDaoImpl<EducationLevel> implements EducationLevelService {
	@Resource
    private EducationLevelMapper educationLevelMapper;
	
	@Override
    public BaseDao<EducationLevel> getInstince() {
        return this.educationLevelMapper;
    }
	
	/**
     * 查询教育水品
     * @param educationLevel
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryEducationLevels(EducationLevel educationLevel) throws Exception {
		List<EducationLevel> educationLevels = educationLevelMapper.queryEducationLevels(educationLevel);	//开始查询，没有条件则查询所有基础用户信息
		if (educationLevels != null && educationLevels.size() > 0) {	//是否查询到数据
			return R.ok().setData(educationLevels).setMsg("查询教育水平成功");
		} else {
			return R.ok().setMsg("没有查询到教育水平");
		}
    }
}
