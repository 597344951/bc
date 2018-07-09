package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyMembershipDuesManage;

public interface PartyMembershipDuesManageService {
	int deleteByPrimaryKey(Integer id);

    int insert(PartyMembershipDuesManage record);

    int insertSelective(PartyMembershipDuesManage record);

    PartyMembershipDuesManage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PartyMembershipDuesManage record);

    int updateByPrimaryKey(PartyMembershipDuesManage record);
    
    /**
     * 查询党费缴纳记录
     * @param conditionMaps
     * @return
     */
    public R queryPartyMembershipDues(Map<String, Object> conditionMaps, int pageNum, int pageSize);
}
