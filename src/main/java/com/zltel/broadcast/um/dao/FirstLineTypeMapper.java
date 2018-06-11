package com.zltel.broadcast.um.dao;

import java.util.List;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.FirstLineType;

public interface FirstLineTypeMapper extends BaseDao<FirstLineType> {
    int deleteByPrimaryKey(Integer fltId);

    int insert(FirstLineType record);

    int insertSelective(FirstLineType record);

    FirstLineType selectByPrimaryKey(Integer fltId);

    int updateByPrimaryKeySelective(FirstLineType record);

    int updateByPrimaryKey(FirstLineType record);
    
    /**
     * 查询一线情况
     * @param firstLineType
     * @return
     */
    public List<FirstLineType> queryFirstLineTypes(FirstLineType firstLineType);
}