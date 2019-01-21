package com.zltel.broadcast.um.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zltel.broadcast.um.dao.IntegralConstituteMapper;
import com.zltel.broadcast.um.dao.PartyIntegralRecordMapper;
import com.zltel.broadcast.um.service.IntegralConstituteService;
import com.zltel.broadcast.um.service.RankingListService;
import com.zltel.broadcast.um.util.DateUtil;

import io.netty.util.internal.StringUtil;

/**
 * 排行榜
 * @author 张毅
 * @since jdk 1.8.0_172
 * Date: 2019.1.16
 */
@Service
public class RankingListServiceImpl implements RankingListService {
	
	@Resource
    private IntegralConstituteMapper integralConstituteMapper;
	@Resource
    private IntegralConstituteService integralConstituteService;
	@Resource
    private PartyIntegralRecordMapper partyIntegralRecordMapper;
	
	/**
	 * 查询积分排行榜
	 * @param condition
	 * @return
	 */
	@Override
	public Map<String, Object> queryIntegralRanking(Map<String, Object> condition) {
		if (StringUtil.isNullOrEmpty(String.valueOf(condition.get("orgId")))) {
			return null;
		}
		Map<BigDecimal, Map<String, Object>> rankingList = new TreeMap<>(
			new Comparator<BigDecimal>() {
                public int compare(BigDecimal first, BigDecimal second) {
                    return second.compareTo(first);
                }
            }
		);
		
		Map<String, Object> queryOrgAllUsersCondition = new HashMap<>();
		queryOrgAllUsersCondition.put("orgId", condition.get("orgId"));
		List<Map<String, Object>> allUsers = 
				integralConstituteMapper.queryPartyUserInfoAndIcInfo(queryOrgAllUsersCondition);	//组织内所有成员
		Map<String, Object> integralInfoCondition = new HashMap<>();
		integralInfoCondition.put("orgId", condition.get("orgId"));
		integralInfoCondition.put("parentIcId", -1);
		Map<String, Object> integralInfo = 
				integralConstituteService.queryOrgIntegralInfo(integralInfoCondition);	//积分信息
		
		if (!(boolean)integralInfo.get("integralError")) {
			if (allUsers != null && allUsers.size() > 0) {
				for (Map<String, Object> user : allUsers) {
					BigDecimal totalIntegral = new BigDecimal(String.valueOf(integralInfo.get("integralCount")));
			    	BigDecimal nowIntegral = totalIntegral;
			    	Map<String, Object> queryCondition = new HashMap<>();
			    	//计算要开始搜索年初到时间之前的积分变化后的情况
			    	queryCondition.put("orgId", condition.get("orgId"));
					queryCondition.put("idCard", user.get("idCard"));
					Calendar ca = Calendar.getInstance();
					ca.setTime(new Date());
					queryCondition.put("endTime", ca.getTime());
					ca.set(Calendar.MONTH, 0); //调到1月
					queryCondition.put("startTime", DateUtil.getDateOfMonthStartDayTime(ca.getTime()));
					List<Map<String, Object>> partyIntegralRecords = partyIntegralRecordMapper.queryPartyIntegralRecords(queryCondition);
					if (partyIntegralRecords != null && partyIntegralRecords.size() > 0) {
						for (Map<String, Object> priMap : partyIntegralRecords) {
							BigDecimal changeIntegral = new BigDecimal(String.valueOf(priMap.get("changeScore")));
							nowIntegral = nowIntegral.add(changeIntegral);
							nowIntegral = nowIntegral.compareTo(new BigDecimal(0)) == -1 ? new BigDecimal(0) : 
								nowIntegral.compareTo(totalIntegral) == 1 ? totalIntegral : nowIntegral;
						}
					}
					Map<String, Object> rankingUser = new HashMap<>();
					if (rankingList.get(nowIntegral) != null) {
						rankingList.get(nowIntegral).put("name", rankingList.get(nowIntegral).get("name") + "、" + user.get("name"));
					} else {
						rankingUser.put("nowIntegral", nowIntegral);
						rankingUser.put("name", user.get("name"));
						rankingList.put(nowIntegral, rankingUser);
					}
				}
				
				List<BigDecimal> datas = new ArrayList<>();
		    	List<String> lines = new ArrayList<>();
		    	List<Map<String, Object>> tables = new ArrayList<>();
		    	
		    	if (rankingList != null && rankingList.keySet().size() > 0) {
		    		for (BigDecimal integral : rankingList.keySet()) {
						Map<String, Object> rankingUser = rankingList.get(integral);
						datas.add(integral);
						lines.add(String.valueOf(rankingUser.get("name")));
						Map<String, Object> table = new HashMap<>();
						table.put("integral", integral);
						table.put("name", String.valueOf(rankingUser.get("name")));
						tables.add(table);
						
						if (datas.size() > 10) {
							break;
						}
					}
		    	}
		    	Map<String, Object> result = new HashMap<>();
		    	result.put("lines", lines);
		    	result.put("datas", datas);
		    	result.put("tables", tables);
		    	
		    	return result;
			}
		}
		return null;
	}
}
