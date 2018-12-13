package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.FlowParty;

public interface FlowPartyService {
	int deleteByPrimaryKey(Integer id);

    int insert(FlowParty record);

    int insertSelective(FlowParty record);

    FlowParty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowParty record);

    int updateByPrimaryKeyWithBLOBs(FlowParty record);

    int updateByPrimaryKey(FlowParty record);
    
    /**
     * 查询流动党员记录
     * @param condition
     * @return
     */
    public R queryFlowPartys(Map<String, Object> condition, int pageNum, int pageSize);
    
    /**
     * 添加流动党员
     * @param request
     * @param partyUser
     * @throws Exception
     */
    public R insertFlowPartyUserInfo(Map<String, Object> flowPartyUser) throws Exception;
    
    /**
     * 添加流动党员
     * @param request
     * @param partyUser
     * @throws Exception
     */
    public R insertFlowPartyUserInfoThisOrg(Map<String, Object> flowPartyUser) throws Exception;
}
