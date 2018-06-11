package com.zltel.broadcast.um.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.AcademicDegreeLevel;
import com.zltel.broadcast.um.dao.AcademicDegreeLevelMapper;
import com.zltel.broadcast.um.service.AcademicDegreeLevelService;

@Service
public class AcademicDegreeLevelServiceImpl extends BaseDaoImpl<AcademicDegreeLevel> implements AcademicDegreeLevelService {
	@Resource
    private AcademicDegreeLevelMapper academicDegreeLevelMapper;
	
	@Override
    public BaseDao<AcademicDegreeLevel> getInstince() {
        return this.academicDegreeLevelMapper;
    }
	
	/**
     * 查询学位水品
     * @param academicDegreeLevel
     * @return
     */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
    public R queryAcademicDegreeLevels(AcademicDegreeLevel academicDegreeLevel) throws Exception {
		List<AcademicDegreeLevel> academicDegreeLevels = academicDegreeLevelMapper.queryAcademicDegreeLevels(academicDegreeLevel);	//开始查询，没有条件则查询所有基础用户信息
		if (academicDegreeLevels != null && academicDegreeLevels.size() > 0) {	//是否查询到数据
			return R.ok().setData(academicDegreeLevels).setMsg("查询学位水平成功");
		} else {
			return R.ok().setMsg("没有查询到学位水平");
		}
    }
}
