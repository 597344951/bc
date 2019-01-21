package com.zltel.broadcast.um.dao;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.IntegralChangeScene;

public interface IntegralChangeSceneMapper extends BaseDao<IntegralChangeScene> {
    int deleteByPrimaryKey(Integer id);

    int insert(IntegralChangeScene record);

    int insertSelective(IntegralChangeScene record);

    IntegralChangeScene selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IntegralChangeScene record);

    int updateByPrimaryKey(IntegralChangeScene record);
}