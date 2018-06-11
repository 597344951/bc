package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.EducationLevel;

public interface EducationLevelService {
	int deleteByPrimaryKey(Integer uid);

    int insert(EducationLevel record);

    int insertSelective(EducationLevel record);

    EducationLevel selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(EducationLevel record);

    int updateByPrimaryKey(EducationLevel record);
	
	/**
     * 查询教育水品
     * @param educationLevel
     * @return
     */
    public R queryEducationLevels(EducationLevel educationLevel) throws Exception;
}
