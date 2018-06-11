package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.AcademicDegreeLevel;

public interface AcademicDegreeLevelMapper extends BaseDao<AcademicDegreeLevel> {
    int deleteByPrimaryKey(Integer adDAid);

    int insert(AcademicDegreeLevel record);

    int insertSelective(AcademicDegreeLevel record);

    AcademicDegreeLevel selectByPrimaryKey(Integer adDAid);

    int updateByPrimaryKeySelective(AcademicDegreeLevel record);

    int updateByPrimaryKey(AcademicDegreeLevel record);
    
    /**
     * 查询学位水品
     * @param academicDegreeLevel
     * @return
     */
    public List<AcademicDegreeLevel> queryAcademicDegreeLevels(AcademicDegreeLevel academicDegreeLevel);
}