package com.zltel.broadcast.um.service.impl;

import java.util.Date;
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
import com.zltel.broadcast.um.util.DateUtil;

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
			for (Map<String, Object> map : partyIntegralRecordsForPageInfo.getList()) {
				map.put("changeTime", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD_HH_MM_SS, map.get("changeTime") == null ||
					map.get("changeTime") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD_HH_MM_SS, map.get("changeTime").toString())));
			}
			return R.ok().setData(partyIntegralRecordsForPageInfo).setMsg("查询积分记录成功");
		} else {
			return R.ok().setMsg("没有查询到积分记录信息");
		}
    }
    
    /**
     * 添加积分变更记录
     * @param ict
     * @return
     */
    public R insertPartyUserIntegralRecord(PartyIntegralRecord pir) {
    	pir.setChangeTime(pir.getChangeTime() == null ? new Date() : pir.getChangeTime());
    	int count = partyIntegralRecordMapper.insertSelective(pir);
    	if(count == 1) {
    		return R.ok().setMsg("添加成功");
    	} else {
    		return R.error().setMsg("添加失败");
    	}
    }
    
    /**
     * 活动变更积分值
     * @param pir  积分变更信息
     * @param icType  积分变更项
     * @param changeIcType  变更方法（加分，扣分）
     * @return
     */
    public boolean automaticIntegralRecord(PartyIntegralRecord pir, IcType icType, IcChangeType icChangeType) {
    	return false;
    }
}
