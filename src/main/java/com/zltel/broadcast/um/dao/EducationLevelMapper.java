package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.EducationLevel;

public interface EducationLevelMapper extends BaseDao<EducationLevel> {
    int deleteByPrimaryKey(Integer eduLevelEid);

    int insert(EducationLevel record);

    int insertSelective(EducationLevel record);

    EducationLevel selectByPrimaryKey(Integer eduLevelEid);

    int updateByPrimaryKeySelective(EducationLevel record);

    int updateByPrimaryKey(EducationLevel record);
    
    /**
     * 查询教育水品
     * @param educationLevel
     * @return
     */
    public List<EducationLevel> queryEducationLevels(EducationLevel educationLevel);
}