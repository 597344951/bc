package com.zltel.broadcast.um.dao;

import java.util.List;
import java.util.Map;

import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;

public interface PartyIntegralRecordMapper extends BaseDao<PartyIntegralRecord> {
    int deleteByPrimaryKey(Integer pirId);

    int insert(PartyIntegralRecord record);

    int insertSelective(PartyIntegralRecord record);

    PartyIntegralRecord selectByPrimaryKey(Integer pirId);

    int updateByPrimaryKeySelective(PartyIntegralRecord record);

    int updateByPrimaryKey(PartyIntegralRecord record);
    
    /**
     * 查询积分记录
     * @param conditions
     * @return
     */
    public List<Map<String, Object>> queryPartyIntegralRecords(Map<String, Object> conditions);
}