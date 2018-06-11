package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.NationType;

public interface NationTypeMapper extends BaseDao<NationType> {
    int deleteByPrimaryKey(Integer ntId);

    int insert(NationType record);

    int insertSelective(NationType record);

    NationType selectByPrimaryKey(Integer ntId);

    int updateByPrimaryKeySelective(NationType record);

    int updateByPrimaryKey(NationType record);
    
    /**
     * 查询民族
     * @param nationType
     * @return
     */
    public List<NationType> queryNationType(NationType nationType);
}