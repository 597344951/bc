package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.FlowPartyRecord;
import com.zltel.broadcast.um.dao.FlowPartyRecordMapper;
import com.zltel.broadcast.um.service.FlowPartyRecordService;
import com.zltel.broadcast.um.util.DateUtil;

import io.netty.util.internal.StringUtil;

@Service
public class FlowPartyRecordServiceImpl extends BaseDaoImpl<FlowPartyRecord> implements FlowPartyRecordService {
	@Resource
    private FlowPartyRecordMapper flowPartyRecordMapper;
	
	@Override
    public BaseDao<FlowPartyRecord> getInstince() {
        return this.flowPartyRecordMapper;
    }
	
	
	/**
     * 查询流动记录
     * @param condition
     * @return
     */
    public R queryFlowPartyRecords(Map<String, Object> condition) {
		List<Map<String, Object>> flowPartyRecords = flowPartyRecordMapper.queryFlowPartyRecords(condition);
		if (flowPartyRecords != null && flowPartyRecords.size() > 0) {
			for (Map<String, Object> map : flowPartyRecords) {
				map.put("contactTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, 
					StringUtil.isNullOrEmpty(String.valueOf(map.get("contactTime"))) 
					? null : (Date)map.get("contactTime")));
			}
		} 
		return R.ok().setData(flowPartyRecords);
    }
    
    /**
     * 查询流动记录
     * @param condition
     * @return
     */
    public R insertFlowPartyRecords(FlowPartyRecord fp) {
    	flowPartyRecordMapper.insertSelective(fp);
    	return R.ok().setMsg("添加成功");
    }
}
