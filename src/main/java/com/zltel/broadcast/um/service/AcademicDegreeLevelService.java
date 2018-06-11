package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.AcademicDegreeLevel;

public interface AcademicDegreeLevelService {
	int deleteByPrimaryKey(Integer uid);

    int insert(AcademicDegreeLevel record);

    int insertSelective(AcademicDegreeLevel record);

    AcademicDegreeLevel selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(AcademicDegreeLevel record);

    int updateByPrimaryKey(AcademicDegreeLevel record);
	
	
	/**
     * 查询学位水品
     * @param academicDegreeLevel
     * @return
     */
    public R queryAcademicDegreeLevels(AcademicDegreeLevel academicDegreeLevel) throws Exception;
}
