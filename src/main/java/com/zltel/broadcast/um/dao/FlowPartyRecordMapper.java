package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.FlowPartyRecord;

public interface FlowPartyRecordMapper extends BaseDao<FlowPartyRecord> {
    int deleteByPrimaryKey(Integer id);

    int insert(FlowPartyRecord record);

    int insertSelective(FlowPartyRecord record);

    FlowPartyRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlowPartyRecord record);

    int updateByPrimaryKey(FlowPartyRecord record);
    
    /**
     * 查询流动记录
     * @param condition
     * @return
     */
    public List<Map<String, Object>> queryFlowPartyRecords(Map<String, Object> condition);
}