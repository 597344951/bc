package com.zltel.broadcast.um.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.OffLinePayRecord;
import com.zltel.broadcast.um.dao.OffLinePayRecordMapper;
import com.zltel.broadcast.um.service.OffLinePayRecordService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class OffLinePayRecordServiceImpl extends BaseDaoImpl<OffLinePayRecord> implements OffLinePayRecordService {
	@Resource
    private OffLinePayRecordMapper offLinePayRecordMapper;
	
	@Override
    public BaseDao<OffLinePayRecord> getInstince() {
        return this.offLinePayRecordMapper;
    }

	@Override
	public R insertOffLinePayRecord(OffLinePayRecord offlpr) {
		offlpr.setThisTime(new Date());
		int result = offLinePayRecordMapper.insertSelective(offlpr);
		if (result == 1) {
			return R.ok().setMsg("添加成功");
		}
		return R.error().setMsg("添加失败");
	}
	
	/**
     * 查询
     * @param condition
     * @return
     */
    public R queryOffLinePayRecord(Map<String, Object> condition) {
    	List<Map<String, Object>> olprs = offLinePayRecordMapper.queryOffLinePayRecord(condition);
    	if (olprs == null || olprs.size() == 0) {
    		return R.error().setMsg("没有查询到记录信息");
    	}
    	for (Map<String, Object> map : olprs) {
    		map.put("payDate", 
				DateUtil.formatDate(DateUtil.YYYY_MM_DD, map.get("payDate") == null ||
				map.get("payDate") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, map.get("payDate").toString())));
    		map.put("payMonthly", 
				DateUtil.formatDate(DateUtil.YYYY_MM, map.get("payMonthly") == null ||
				map.get("payMonthly") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM, map.get("payMonthly").toString())));
		}
    	return R.ok().setData(olprs);
    }
}
