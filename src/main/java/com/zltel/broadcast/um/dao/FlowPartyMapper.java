package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.FlowParty;

public interface FlowPartyMapper extends BaseDao<FlowParty> {
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
    public List<Map<String, Object>> queryFlowPartys(Map<String, Object> condition);
}