package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.PartyMembershipDuesManage;

public interface PartyMembershipDuesManageMapper extends BaseDao<PartyMembershipDuesManage> {
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
    public List<Map<String, Object>> queryPartyMembershipDues(Map<String, Object> conditionMaps);
    
    /**
     * 查询党费缴纳记录里的党组织
     * @param conditionMaps
     * @return
     */
    public List<Map<String, Object>> queryOrgInfoOfPMDM(Map<String, Object> conditionMaps);
}