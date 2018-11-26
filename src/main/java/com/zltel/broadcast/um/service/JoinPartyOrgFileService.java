package com.zltel.broadcast.um.service;

import com.zltel.broadcast.um.bean.JoinPartyOrgFile;

public interface JoinPartyOrgFileService {
	int deleteByPrimaryKey(Integer id);

    int insert(JoinPartyOrgFile record);

    int insertSelective(JoinPartyOrgFile record);

    JoinPartyOrgFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JoinPartyOrgFile record);

    int updateByPrimaryKey(JoinPartyOrgFile record);
}
