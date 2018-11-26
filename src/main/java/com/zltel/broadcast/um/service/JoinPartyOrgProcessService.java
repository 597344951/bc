package com.zltel.broadcast.um.service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.JoinPartyOrgProcess;

public interface JoinPartyOrgProcessService {
	int deleteByPrimaryKey(Integer id);

    int insert(JoinPartyOrgProcess record);

    int insertSelective(JoinPartyOrgProcess record);

    JoinPartyOrgProcess selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JoinPartyOrgProcess record);

    int updateByPrimaryKey(JoinPartyOrgProcess record);
    
    /**
     * 查询加入党的步骤
     * @param jpop
     * @return
     */
    public R queryJoinPartyOrgProcess(JoinPartyOrgProcess jpop);
}
