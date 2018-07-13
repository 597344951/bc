package com.zltel.broadcast.um.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.PartyIntegralRecord;
import com.zltel.broadcast.um.dao.PartyIntegralRecordMapper;
import com.zltel.broadcast.um.service.PartyIntegralRecordService;

@Service
public class PartyIntegralRecordServiceImpl extends BaseDaoImpl<PartyIntegralRecord> implements PartyIntegralRecordService {
	@Resource
    private PartyIntegralRecordMapper partyIntegralRecordMapper;
	@Override
    public BaseDao<PartyIntegralRecord> getInstince() {
        return this.partyIntegralRecordMapper;
    }
	
	/**
     * 查询积分记录
     * @param conditions
     * @return
     */
    public R queryPartyIntegralRecords(Map<String, Object> conditions, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> partyIntegralRecords = partyIntegralRecordMapper.queryPartyIntegralRecords(conditions);
		PageInfo<Map<String, Object>> partyIntegralRecordsForPageInfo = new PageInfo<>(partyIntegralRecords);
		if (partyIntegralRecordsForPageInfo != null && partyIntegralRecordsForPageInfo.getList() != null 
				&& partyIntegralRecordsForPageInfo.getList().size() > 0) {
			return R.ok().setData(partyIntegralRecordsForPageInfo).setMsg("查询积分记录成功");
		} else {
			return R.ok().setMsg("没有查询到积分记录信息");
		}
    }
}
