package com.zltel.broadcast.log.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.log.bean.LogType;

public interface LogTypeMapper extends BaseDao<LogType>{
    int deleteByPrimaryKey(Integer tid);

    int insert(LogType record);

    int insertSelective(LogType record);

    LogType selectByPrimaryKey(Integer tid);

    int updateByPrimaryKeySelective(LogType record);

    int updateByPrimaryKey(LogType record);

}