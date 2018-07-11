package com.zltel.broadcast.um.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zltel.broadcast.common.json.R;
import com.zltel.broadcast.common.support.BaseDao;
import com.zltel.broadcast.common.support.BaseDaoImpl;
import com.zltel.broadcast.um.bean.PartyMembershipDuesManage;
import com.zltel.broadcast.um.bean.PartyMembershipDuesStatus;
import com.zltel.broadcast.um.dao.PartyMembershipDuesManageMapper;
import com.zltel.broadcast.um.dao.PartyMembershipDuesStatusMapper;
import com.zltel.broadcast.um.service.PartyMembershipDuesManageService;
import com.zltel.broadcast.um.util.DateUtil;

@Service
public class PartyMembershipDuesManageServiceImpl extends BaseDaoImpl<PartyMembershipDuesManage> 
															implements PartyMembershipDuesManageService {
	
	@Resource
    private PartyMembershipDuesManageMapper partyMembershipDuesManageMapper;
	@Resource
    private PartyMembershipDuesStatusMapper partyMembershipDuesStatusMapper;
	@Override
    public BaseDao<PartyMembershipDuesManage> getInstince() {
        return this.partyMembershipDuesManageMapper;
    }
	
	/**
     * 查询党费缴纳记录
     * @param conditionMaps
     * @return
     */
    public R queryPartyMembershipDues(Map<String, Object> conditionMaps, int pageNum, int pageSize) {
    	if (conditionMaps.get("payDate") != null && conditionMaps.get("payDate") != "") {
    		conditionMaps.put("shouldPayDateStart", DateUtil.toDate(DateUtil.YYYY_MM_DD, conditionMaps.get("payDate").toString()));
    		conditionMaps.put("shouldPayDateEnd", DateUtil.getMonthEnd(DateUtil.toDate(DateUtil.YYYY_MM_DD, conditionMaps.get("payDate").toString())));
    		conditionMaps.remove("payDate");
    	}
    	
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> partyMembershipDues = partyMembershipDuesManageMapper.queryPartyMembershipDues(conditionMaps);	//开始查询，没有条件则查询所有
		PageInfo<Map<String, Object>> partyMembershipDuesForPageInfo = new PageInfo<>(partyMembershipDues);
		if (partyMembershipDuesForPageInfo != null && partyMembershipDuesForPageInfo.getList() != null 
				&& partyMembershipDuesForPageInfo.getList().size() > 0) {	//是否查询到数据
			for (Map<String, Object> map : partyMembershipDuesForPageInfo.getList()) {
				map.put("shouldPayDateStart", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateStart") == null ||
					map.get("shouldPayDateStart") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateStart").toString())));
				map.put("shouldPayDateEnd", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateEnd") == null ||
					map.get("shouldPayDateEnd") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, map.get("shouldPayDateEnd").toString())));
				map.put("paidDate", 
					DateUtil.formatDate(DateUtil.YYYY_MM_DD, map.get("paidDate") == null ||
					map.get("paidDate") == "" ? null : DateUtil.toDate(DateUtil.YYYY_MM_DD, map.get("paidDate").toString())));
			}
			
			return R.ok().setData(partyMembershipDuesForPageInfo).setMsg("查询党费缴纳记录成功");
		} else {
			return R.ok().setMsg("没有查询到党费缴纳记录");
		}
    }
    
    /**
     * 查询党费缴纳记录里的党组织
     * @param conditionMaps
     * @return
     */
    public R queryOrgInfoOfPMDM(Map<String, Object> conditionMaps, int pageNum, int pageSize) {
    	PageHelper.startPage(pageNum, pageSize);
		List<Map<String, Object>> orgInfos = partyMembershipDuesManageMapper.queryOrgInfoOfPMDM(conditionMaps);	//开始查询，没有条件则查询所有组织关系
		PageInfo<Map<String, Object>> orgInfosForPageInfo = new PageInfo<>(orgInfos);
		if (orgInfosForPageInfo != null && orgInfosForPageInfo.getList() != null && orgInfosForPageInfo.getList().size() > 0) {	//是否查询到数据
			return R.ok().setData(orgInfosForPageInfo).setMsg("查询党组织成功");
		} else {
			return R.ok().setMsg("没有查询到党组织");
		}
    }
    
    /**
     * 查询此组织的缴费统计
     * @param conditionMaps
     * @return
     */
    public R queryPMDMChartForOrgInfo(Map<String, Object> conditionMaps) {
    	if (conditionMaps.get("orgInfoId") == null && conditionMaps.get("orgInfoId") == "") return R.ok().setMsg("没有查询到数据");
    	Date startTime = null;
    	Date endTime = null;
    	if (conditionMaps.get("shouldPayDateStart") != null && conditionMaps.get("shouldPayDateStart") != "") {
    		startTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("shouldPayDateStart"));
    		if (conditionMaps.get("shouldPayDateEnd") != null && conditionMaps.get("shouldPayDateEnd") != "") {	//两个都有值
    			endTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("shouldPayDateEnd"));
    			if (endTime.before(startTime)) {
    				return R.error().setMsg("时间选择错误");
    			}
    		} else {
    			Calendar ca = Calendar.getInstance();    
    			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
    			endTime = ca.getTime();
    		}
    	} else if (conditionMaps.get("shouldPayDateEnd") != null && conditionMaps.get("shouldPayDateEnd") != "") {
    		Calendar ca = Calendar.getInstance();    
			ca.set(Calendar.YEAR, -1);
			startTime = ca.getTime();
			endTime = DateUtil.toDate(DateUtil.YYYY_MM_DD, (String)conditionMaps.get("shouldPayDateEnd"));
    	} else {
    		Calendar ca = Calendar.getInstance();    
			ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
			endTime = ca.getTime();
			ca.set(Calendar.YEAR, -1);
			ca.set(Calendar.DAY_OF_MONTH, 1);
			startTime = ca.getTime();
    	}
    	
    	List<PartyMembershipDuesStatus> paidStatus = partyMembershipDuesStatusMapper.queryPMDSs(null);	//查询全部状态
    	
    	List<String> legendDatas = new ArrayList<>();
    	List<String> dateLines = new ArrayList<>();
    	List<List<Integer>> datas = new ArrayList<>();
    	
    	for (PartyMembershipDuesStatus pmds : paidStatus) {	//提示文本
    		legendDatas.add(pmds.getName());
		}
    	
    	while (startTime.before(endTime)) {	//每个月循环
    		Calendar ca = Calendar.getInstance();
    		ca.setTime(startTime);
    		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));	//本月最后一天
    		dateLines.add(String.valueOf(ca.get(Calendar.YEAR)) + "-" + String.valueOf(ca.get(Calendar.MONTH) + 1) + "月");
    		Date temporaryEndTime = ca.getTime();
    		conditionMaps.put("shouldPayDateStart", startTime);
    		conditionMaps.put("shouldPayDateEnd", temporaryEndTime);
    		for (int i = 0; i < paidStatus.size(); i++) {	//查询每个月的支付状态
    			if (datas.size() == i) {
    				List<Integer> data = new ArrayList<>();
    				datas.add(data);
    			}
    			PartyMembershipDuesStatus pmds = paidStatus.get(i);
				conditionMaps.put("payStatus", pmds.getId());
				List<Map<String, Object>> paidStatusCount = partyMembershipDuesManageMapper.queryPartyMembershipDues(conditionMaps);
				datas.get(i).add(paidStatusCount == null ? 0 : paidStatusCount.size());
			}
    		ca.add(Calendar.MONTH, +1);
    		ca.set(Calendar.DAY_OF_MONTH, 1);
    		startTime = ca.getTime();
    	}
    	
    	Map<String, Object> results = new HashMap<>();
    	results.put("legendDatas", legendDatas);
    	results.put("dateLines", dateLines);
    	results.put("datas", datas);
    	
    	return R.ok().setData(results);
    }
}
