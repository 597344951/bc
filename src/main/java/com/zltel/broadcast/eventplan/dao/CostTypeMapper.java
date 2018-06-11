package com.zltel.broadcast.eventplan.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.zltel.broadcast.eventplan.bean.CostType;

public interface CostTypeMapper {
    int deleteByPrimaryKey(Integer costType);

    int insert(CostType record);

    int insertSelective(CostType record);

    CostType selectByPrimaryKey(Integer costType);

    int updateByPrimaryKeySelective(CostType record);

    int updateByPrimaryKey(CostType record);
    
    List<CostType> query(CostType record,RowBounds rb);
}