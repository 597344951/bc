package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;

public interface PartyIntegralRecordService {
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
    public R queryPartyIntegralRecords(Map<String, Object> conditions, int pageNum, int pageSize);
    
    /**
     * 添加积分变更记录
     * @param ict
     * @return
     */
    public R insertPartyUserIntegralRecord(PartyIntegralRecord pir);
}
