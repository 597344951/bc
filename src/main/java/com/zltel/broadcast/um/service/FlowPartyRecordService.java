package com.zltel.broadcast.um.service;

import java.util.Map;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.um.bean.FlowPartyRecord;

public interface FlowPartyRecordService {
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
    public R queryFlowPartyRecords(Map<String, Object> condition);
    
    /**
     * 查询流动记录
     * @param condition
     * @return
     */
    public R insertFlowPartyRecords(FlowPartyRecord fp);
}
