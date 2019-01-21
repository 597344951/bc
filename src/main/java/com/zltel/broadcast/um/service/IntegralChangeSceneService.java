package com.zltel.broadcast.um.service;

import com.zltel.broadcast.um.bean.IntegralChangeScene;

public interface IntegralChangeSceneService {
	int deleteByPrimaryKey(Integer id);

    int insert(IntegralChangeScene record);

    int insertSelective(IntegralChangeScene record);

    IntegralChangeScene selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IntegralChangeScene record);

    int updateByPrimaryKey(IntegralChangeScene record);
}
