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
    
    /**
     * 查询党费缴纳记录里的党组织
     * @param conditionMaps
     * @return
     */
    public R queryOrgInfoOfPMDM(Map<String, Object> conditionMaps, int pageNum, int pageSize);
    
    /**
     * 查询此组织的缴费统计
     * @param conditionMaps
     * @return
     */
    public R queryPMDMChartForOrgInfo(Map<String, Object> conditionMaps);
}
