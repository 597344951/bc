package com.zltel.broadcast.um.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.TurnOutOrgProcess;
import com.zltel.broadcast.um.dao.TurnOutOrgProcessMapper;
import com.zltel.broadcast.um.service.TurnOutOrgProcessService;

@Service
public class TurnOutOrgProcessServiceImpl extends BaseDaoImpl<TurnOutOrgProcess> implements TurnOutOrgProcessService {
	@Resource
    private TurnOutOrgProcessMapper turnOutOrgProcessMapper;
	
	@Override
    public BaseDao<TurnOutOrgProcess> getInstince() {
        return this.turnOutOrgProcessMapper;
    }
}
