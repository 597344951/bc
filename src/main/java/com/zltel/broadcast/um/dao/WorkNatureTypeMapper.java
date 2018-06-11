package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.WorkNatureType;

public interface WorkNatureTypeMapper extends BaseDao<WorkNatureType> {
    int deleteByPrimaryKey(Integer workNatureId);

    int insert(WorkNatureType record);

    int insertSelective(WorkNatureType record);

    WorkNatureType selectByPrimaryKey(Integer workNatureId);

    int updateByPrimaryKeySelective(WorkNatureType record);

    int updateByPrimaryKey(WorkNatureType record);
    
    /**
     * 查询工作性质
     * @param workNatureType
     * @return
     */
    public List<WorkNatureType> queryWorkNatureTypes(WorkNatureType workNatureType);
}