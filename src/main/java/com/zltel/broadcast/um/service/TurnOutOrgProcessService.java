package com.zltel.broadcast.um.service;

import com.zltel.broadcast.um.bean.TurnOutOrgProcess;

public interface TurnOutOrgProcessService {
	int deleteByPrimaryKey(Integer id);

    int insert(TurnOutOrgProcess record);

    int insertSelective(TurnOutOrgProcess record);

    TurnOutOrgProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TurnOutOrgProcess record);

    int updateByPrimaryKey(TurnOutOrgProcess record);
}
