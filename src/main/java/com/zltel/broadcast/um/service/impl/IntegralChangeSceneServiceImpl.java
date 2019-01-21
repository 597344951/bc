package com.zltel.broadcast.um.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.IntegralChangeScene;
import com.zltel.broadcast.um.dao.IntegralChangeSceneMapper;
import com.zltel.broadcast.um.service.IntegralChangeSceneService;

@Service
public class IntegralChangeSceneServiceImpl extends BaseDaoImpl<IntegralChangeScene> implements IntegralChangeSceneService {
	@Resource
    private IntegralChangeSceneMapper integralChangeSceneMapper;
	@Override
    public BaseDao<IntegralChangeScene> getInstince() {
        return this.integralChangeSceneMapper;
    }
}
