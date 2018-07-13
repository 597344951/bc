package com.zltel.broadcast.um.dao;

import com.zltel.broadcast.um.bean.IntegralChangeType;

public interface IntegralChangeTypeMapper {
    int deleteByPrimaryKey(Integer ictId);

    int insert(IntegralChangeType record);

    int insertSelective(IntegralChangeType record);

    IntegralChangeType selectByPrimaryKey(Integer ictId);

    int updateByPrimaryKeySelective(IntegralChangeType record);

    int updateByPrimaryKey(IntegralChangeType record);
}